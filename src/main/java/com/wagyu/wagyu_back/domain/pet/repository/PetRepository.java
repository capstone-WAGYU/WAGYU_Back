package com.wagyu.wagyu_back.domain.pet.repository;

import com.wagyu.wagyu_back.domain.pet.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PetRepository extends JpaRepository<Pet, Long> {
    Optional<Integer> countAllByOwnerIdAndIsDeletedFalse(Long ownerId);
    Optional<Pet> findByIdAndIsDeletedFalse(Long id);
    List<Pet> findAllByOwnerIdAndIsDeletedFalse(Long ownerId);
}
