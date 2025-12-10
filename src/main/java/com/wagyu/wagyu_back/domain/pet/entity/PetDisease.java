package com.wagyu.wagyu_back.domain.pet.entity;

import com.wagyu.wagyu_back.domain.disease.entity.Disease;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pets_diseases")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PetDisease {
    @EmbeddedId
    private PetDiseaseId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("petId")
    @JoinColumn(name = "pet_id")
    private Pet pet;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("diseaseId")
    @JoinColumn(name = "disease_id")
    private Disease disease;
}
