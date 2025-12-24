package com.wagyu.wagyu_back.domain.auth.service;

import static com.wagyu.wagyu_back.global.security.TokenConstants.*;

import com.wagyu.wagyu_back.domain.auth.dto.request.LoginRequestDTO;
import com.wagyu.wagyu_back.domain.auth.dto.request.RefreshRequestDTO;
import com.wagyu.wagyu_back.domain.auth.dto.request.RegisterRequestDTO;
import com.wagyu.wagyu_back.domain.auth.dto.response.LoginResponseDTO;
import com.wagyu.wagyu_back.domain.auth.dto.response.RefreshResponseDTO;
import com.wagyu.wagyu_back.domain.auth.entity.RefreshToken;
import com.wagyu.wagyu_back.domain.auth.enums.AuthLevel;
import com.wagyu.wagyu_back.domain.auth.repository.RefreshTokenRepository;
import com.wagyu.wagyu_back.domain.user.entity.User;
import com.wagyu.wagyu_back.domain.user.repository.UserRepository;
import com.wagyu.wagyu_back.global.exception.CustomException;
import com.wagyu.wagyu_back.global.exception.ErrorCode;
import com.wagyu.wagyu_back.global.security.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;

    @Transactional
    public void register(RegisterRequestDTO registerRequestDTO) {
        if (userRepository.existsByUsername(registerRequestDTO.getUsername())) {
            throw new CustomException(ErrorCode.DUPLICATE_USERNAME);
        }

        User user = User.builder()
                .username(registerRequestDTO.getUsername())
                .password(passwordEncoder.encode(registerRequestDTO.getPassword()))
                .nickname(registerRequestDTO.getNickname())
                .authLevel(AuthLevel.USER)
                .build();

        userRepository.save(user);
    }

    @Transactional
    public LoginResponseDTO login(LoginRequestDTO dto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword())
        );

        String accessToken = tokenProvider.createToken(dto.getUsername());
        String refreshToken = tokenProvider.createRefreshToken(dto.getUsername());

        User user = userRepository.findByUsernameAndIsDeletedFalse(dto.getUsername())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        RefreshToken rf = RefreshToken.builder()
                .user(user)
                .token(refreshToken)
                .expiryDate(LocalDateTime.now().plus(REFRESH_TOKEN_VALIDITY, ChronoUnit.MILLIS))
                .build();
        refreshTokenRepository.save(rf);

        return LoginResponseDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Transactional(readOnly = true)
    public RefreshResponseDTO refresh(RefreshRequestDTO dto) {
        if (!tokenProvider.validateToken(dto.getRefreshToken())) {
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }

        RefreshToken refreshToken = refreshTokenRepository.findByToken(dto.getRefreshToken())
                .orElseThrow(() -> new CustomException(ErrorCode.TOKEN_NOT_FOUND));

        if (refreshToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new CustomException(ErrorCode.TOKEN_EXPIRED);
        }

        String username = tokenProvider.getUsername(refreshToken.getToken());
        String newAccessToken = tokenProvider.createToken(username);

        return RefreshResponseDTO.builder()
                .accessToken(newAccessToken)
                .build();
    }
}
