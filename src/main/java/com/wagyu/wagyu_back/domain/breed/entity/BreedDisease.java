package com.wagyu.wagyu_back.domain.breed.entity;

import com.wagyu.wagyu_back.domain.disease.entity.Disease;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "breeds_diseases")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BreedDisease {
    @EmbeddedId
    private BreedDiseaseId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("breedId")
    @JoinColumn(name = "breed_id")
    private Breed breed;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("diseaseId")
    @JoinColumn(name = "disease_id")
    private Disease disease;
}
