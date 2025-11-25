package com.wagyu.wagyu_back.domain.pet.service;

import com.wagyu.wagyu_back.domain.pet.dto.PetCreateRequestDTO;
import com.wagyu.wagyu_back.domain.pet.dto.PetListResponseDTO;
import com.wagyu.wagyu_back.domain.pet.dto.PetResponseDTO;
import com.wagyu.wagyu_back.domain.pet.dto.PetUpdateRequestDTO;
import com.wagyu.wagyu_back.domain.pet.entity.Pet;
import com.wagyu.wagyu_back.domain.pet.repository.PetRepository;
import com.wagyu.wagyu_back.domain.user.entity.User;
import com.wagyu.wagyu_back.domain.user.service.UserService;
import com.wagyu.wagyu_back.global.exception.CustomException;
import com.wagyu.wagyu_back.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PetService {
    private final UserService userService;

    private final PetRepository petRepository;

    @Transactional
    public PetListResponseDTO getPets(String username) {
        User owner = userService.findByUsername(username);

        List<Pet> pets = petRepository.findAllByOwnerAndIsDeletedFalse(owner);
        List<PetResponseDTO> petResponseDTOS = pets.stream().map(pet -> PetResponseDTO.builder()
                .id(pet.getId())
                .name(pet.getName())
                .age(pet.getAge())
                .breed(pet.getBreed())
                .gender(pet.getGender())
                .build()
        ).collect(Collectors.toList());
        return PetListResponseDTO.builder().pets(petResponseDTOS).build();
    }

    @Transactional
    public void createPet(String username, PetCreateRequestDTO dto) {
        User owner = userService.findByUsername(username);

        Pet pet = Pet.builder()
                .name(dto.getName())
                .age(dto.getAge())
                .breed(dto.getBreed())
                .gender(dto.getGender())
                .owner(owner)
                .build();
        petRepository.save(pet);
    }

    @Transactional
    public void updatePet(String username, Long petId, PetUpdateRequestDTO dto) {
        User owner = userService.findByUsername(username);
        Pet pet = petRepository.findByIdAndIsDeletedFalse(petId)
                .orElseThrow(() -> new CustomException(ErrorCode.PET_NOT_FOUND));

        if (!pet.getOwner().getId().equals(owner.getId())) {
            throw new CustomException(ErrorCode.FORBIDDEN_PET_UPDATE);
        }
        pet.update(dto.getName(), dto.getAge(), dto.getBreed(), dto.getGender());
    }

    @Transactional
    public void deletePet(String username, Long petId) {
        User owner = userService.findByUsername(username);
        Pet pet = petRepository.findByIdAndIsDeletedFalse(petId)
                .orElseThrow(() -> new CustomException(ErrorCode.PET_NOT_FOUND));

        if (!pet.getOwner().getId().equals(owner.getId())) {
            throw new CustomException(ErrorCode.FORBIDDEN_PET_DELETE);
        }
        pet.delete();
    }
}
