package com.wagyu.wagyu_back.domain.breed.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class BreedDiseaseId implements Serializable {
    private Long breedId;
    private Long diseaseId;
}
