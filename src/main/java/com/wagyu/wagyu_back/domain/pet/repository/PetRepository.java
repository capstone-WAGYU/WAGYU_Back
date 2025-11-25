package com.wagyu.wagyu_back.domain.pet.repository;

import com.wagyu.wagyu_back.domain.pet.entity.Pet;
import com.wagyu.wagyu_back.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PetRepository extends JpaRepository<Pet, Long> {
    List<Pet> findAllByOwnerAndIsDeletedFalse(User owner);
    Optional<Pet> findByIdAndIsDeletedFalse(Long id);
}
