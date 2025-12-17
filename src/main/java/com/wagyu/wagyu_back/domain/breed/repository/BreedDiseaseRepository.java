package com.wagyu.wagyu_back.domain.breed.repository;

import com.wagyu.wagyu_back.domain.breed.entity.BreedDisease;
import com.wagyu.wagyu_back.domain.disease.entity.Disease;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BreedDiseaseRepository extends JpaRepository<BreedDisease, Long> {
    @Query("SELECT bd.disease FROM BreedDisease bd WHERE bd.breed.id = :breedId")
    List<Disease> findDiseaseByBreedId(Long breedId);
}
