package com.wagyu.wagyu_back.domain.auth.controller;

import com.wagyu.wagyu_back.domain.auth.dto.request.LoginRequestDTO;
import com.wagyu.wagyu_back.domain.auth.dto.request.RefreshRequestDTO;
import com.wagyu.wagyu_back.domain.auth.dto.request.RegisterRequestDTO;
import com.wagyu.wagyu_back.domain.auth.dto.response.LoginResponseDTO;
import com.wagyu.wagyu_back.domain.auth.dto.response.RefreshResponseDTO;
import com.wagyu.wagyu_back.domain.auth.service.AuthService;
import com.wagyu.wagyu_back.global.dto.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("register")
    public ResponseEntity<ApiResponse<Void>> register(
            @Valid @RequestBody RegisterRequestDTO registerRequestDTO
    ) {
        authService.register(registerRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("회원가입이 완료되었습니다."));
    }

    @PostMapping("login")
    public ResponseEntity<ApiResponse<LoginResponseDTO>> login(
            @Valid @RequestBody LoginRequestDTO dto
    ) {
        return ResponseEntity.ok(ApiResponse.success(authService.login(dto)));
    }

    @PostMapping("refresh")
    public ResponseEntity<ApiResponse<RefreshResponseDTO>> refresh(
            @Valid @RequestBody RefreshRequestDTO dto
    ) {
        return ResponseEntity.ok(ApiResponse.success(authService.refresh(dto)));
    }
}
