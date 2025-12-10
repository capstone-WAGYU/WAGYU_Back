package com.wagyu.wagyu_back.domain.breed.entity;

import com.wagyu.wagyu_back.domain.breed.enums.BreedSize;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "breeds")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Breed {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BreedSize size;

    @OneToMany(mappedBy = "breed")
    private List<BreedDisease> breedDiseases;
}
