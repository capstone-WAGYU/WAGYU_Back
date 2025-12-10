package com.wagyu.wagyu_back.domain.breed.repository;

import com.wagyu.wagyu_back.domain.breed.entity.Breed;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BreedRepository extends JpaRepository<Breed, Long> {
}
