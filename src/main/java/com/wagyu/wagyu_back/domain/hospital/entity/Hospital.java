package com.wagyu.wagyu_back.domain.hospital.entity;

import com.wagyu.wagyu_back.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "hospitals")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Hospital {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User owner;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 100)
    private String address;

    @Column(name = "is_24_hours", nullable = false)
    private Boolean is24Hours;

    public void update(String name, String address, Boolean is24Hours) {
        this.name = name;
        this.address = address;
        this.is24Hours = is24Hours;
    }
}
