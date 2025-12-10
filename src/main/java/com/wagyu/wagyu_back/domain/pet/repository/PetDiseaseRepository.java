package com.wagyu.wagyu_back.domain.pet.repository;

import com.wagyu.wagyu_back.domain.pet.entity.PetDisease;
import com.wagyu.wagyu_back.domain.pet.entity.PetDiseaseId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PetDiseaseRepository extends JpaRepository<PetDisease, PetDiseaseId> {
    void deleteAllByPetId(Long petId);
}
