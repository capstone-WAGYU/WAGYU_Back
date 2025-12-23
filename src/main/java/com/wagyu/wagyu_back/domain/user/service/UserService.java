package com.wagyu.wagyu_back.domain.user.service;

import com.wagyu.wagyu_back.domain.user.dto.request.UserUpdatePhoneNumRequestDTO;
import com.wagyu.wagyu_back.domain.user.dto.response.UserProfileResponseDTO;
import com.wagyu.wagyu_back.domain.user.entity.User;
import com.wagyu.wagyu_back.domain.user.repository.UserRepository;
import com.wagyu.wagyu_back.global.exception.CustomException;
import com.wagyu.wagyu_back.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public UserProfileResponseDTO getUserProfile(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        return UserProfileResponseDTO.builder()
                .username(user.getUsername())
                .nickname(user.getNickname())
                .phoneNum(user.getPhoneNum())
                .createAt(user.getCreatedAt())
                .updateAt(user.getUpdatedAt())
                .build();
    }

    @Transactional
    public void updatePhoneNum(String username, UserUpdatePhoneNumRequestDTO dto) {
        User user =  userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        user.updatePhoneNum(dto.getPhoneNum());
    }
}
