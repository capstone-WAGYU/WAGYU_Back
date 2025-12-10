package com.wagyu.wagyu_back.domain.disease.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "diseases")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Disease {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String name;
}
