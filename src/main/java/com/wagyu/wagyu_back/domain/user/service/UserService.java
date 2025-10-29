package com.wagyu.wagyu_back.domain.user.service;

import com.wagyu.wagyu_back.domain.auth.enums.Provider;
import com.wagyu.wagyu_back.domain.user.entity.User;
import com.wagyu.wagyu_back.domain.user.repository.UserRepository;
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

    public void save(User user) {
        userRepository.save(user);
    }

}
