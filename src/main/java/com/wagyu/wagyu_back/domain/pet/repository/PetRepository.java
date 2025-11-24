package com.wagyu.wagyu_back.domain.pet.repository;

import com.wagyu.wagyu_back.domain.pet.entity.Pet;
import com.wagyu.wagyu_back.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PetRepository extends JpaRepository<Pet, Long> {
    List<Pet> findAllByOwner(User owner);
}
