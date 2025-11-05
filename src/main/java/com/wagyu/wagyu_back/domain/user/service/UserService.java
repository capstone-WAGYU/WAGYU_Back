package com.wagyu.wagyu_back.domain.user.service;

import com.wagyu.wagyu_back.domain.auth.enums.Provider;
import com.wagyu.wagyu_back.domain.user.entity.User;
import com.wagyu.wagyu_back.domain.user.repository.UserRepository;
import com.wagyu.wagyu_back.global.exception.CustomException;
import com.wagyu.wagyu_back.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean existsByProviderAndProviderId(Provider provider, String providerId) {
        return userRepository.existsByProviderAndProviderId(provider, providerId);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }

    public void save(User user) {
        userRepository.save(user);
    }

}
