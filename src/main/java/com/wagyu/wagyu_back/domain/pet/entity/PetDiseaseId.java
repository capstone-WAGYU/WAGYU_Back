package com.wagyu.wagyu_back.domain.pet.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class PetDiseaseId implements Serializable {
    private Long petId;
    private Long diseaseId;
}
