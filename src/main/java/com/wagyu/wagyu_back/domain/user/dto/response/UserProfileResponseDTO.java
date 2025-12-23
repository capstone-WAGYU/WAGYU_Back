package com.wagyu.wagyu_back.domain.user.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class UserProfileResponseDTO {
    private String username;
    private String nickname;
    private String phoneNum;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
}
