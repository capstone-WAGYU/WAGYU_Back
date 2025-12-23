package com.wagyu.wagyu_back.domain.user.entity;

import com.wagyu.wagyu_back.domain.auth.enums.AuthLevel;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, length = 50)
    private String username;

    @Column
    private String password;

    @Column(nullable = false)
    private String nickname;

    @Column(name = "phone_num")
    private String phoneNum;

    @Column(name = "auth_level", nullable = false)
    @Enumerated(EnumType.STRING)
    private AuthLevel authLevel;

    @Column(name = "created_at")
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @LastModifiedDate
    private LocalDateTime updatedAt;

    public void updatePhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }
}
