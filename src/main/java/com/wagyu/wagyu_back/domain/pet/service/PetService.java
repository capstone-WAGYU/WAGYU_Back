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

@Service
@RequiredArgsConstructor
public class PetService {
    private final UserService userService;

    private final PetRepository petRepository;

    public PetListResponseDTO getPets(String username) {
        User owner = userService.findByUsername(username);

        List<Pet> pets = petRepository.findAllByOwner(owner);
        List<PetResponseDTO> petResponseDTOS = new ArrayList<>();
        for (Pet pet : pets) {
            PetResponseDTO petResponseDTO = PetResponseDTO.builder()
                    .id(pet.getId())
                    .name(pet.getName())
                    .age(pet.getAge())
                    .breed(pet.getBreed())
                    .gender(pet.getGender())
                    .build();
            petResponseDTOS.add(petResponseDTO);
        }
        return PetListResponseDTO.builder().pets(petResponseDTOS).build();
    }

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
    public void updatePet(Long petId, PetUpdateRequestDTO dto) {
        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new CustomException(ErrorCode.PET_NOT_FOUND));
        pet.update(dto.getName(), dto.getAge(), dto.getBreed(), dto.getGender());
    }
}
