package com.wagyu.wagyu_back.domain.pet.service;

import com.wagyu.wagyu_back.domain.breed.dto.response.BreedResponseDTO;
import com.wagyu.wagyu_back.domain.breed.entity.Breed;
import com.wagyu.wagyu_back.domain.breed.repository.BreedRepository;
import com.wagyu.wagyu_back.domain.disease.dto.response.DiseaseResponseDTO;
import com.wagyu.wagyu_back.domain.disease.entity.Disease;
import com.wagyu.wagyu_back.domain.disease.repository.DiseaseRepository;
import com.wagyu.wagyu_back.domain.pet.dto.request.PetCreateRequestDTO;
import com.wagyu.wagyu_back.domain.pet.dto.response.PetListResponseDTO;
import com.wagyu.wagyu_back.domain.pet.dto.response.PetResponseDTO;
import com.wagyu.wagyu_back.domain.pet.dto.request.PetUpdateRequestDTO;
import com.wagyu.wagyu_back.domain.pet.entity.Pet;
import com.wagyu.wagyu_back.domain.pet.entity.PetDisease;
import com.wagyu.wagyu_back.domain.pet.entity.PetDiseaseId;
import com.wagyu.wagyu_back.domain.pet.repository.PetDiseaseRepository;
import com.wagyu.wagyu_back.domain.pet.repository.PetRepository;
import com.wagyu.wagyu_back.domain.user.entity.User;
import com.wagyu.wagyu_back.domain.user.repository.UserRepository;
import com.wagyu.wagyu_back.global.exception.CustomException;
import com.wagyu.wagyu_back.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PetService {
    private final UserRepository userRepository;

    private final PetRepository petRepository;
    private final BreedRepository breedRepository;
    private final DiseaseRepository diseaseRepository;

    private final PetDiseaseRepository petDiseaseRepository;

    private void savePetDisease(Pet pet, List<Disease> diseases) {
        List<PetDisease> petDiseases = diseases.stream().map(disease -> PetDisease.builder()
                .id(new PetDiseaseId(pet.getId(), disease.getId()))
                .pet(pet)
                .disease(disease)
                .build()
        ).toList();
        petDiseaseRepository.saveAll(petDiseases);
    }

    @Transactional(readOnly = true)
    public PetListResponseDTO getPets(String username) {
        User owner = userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        List<Pet> pets = petRepository.findAllByOwnerIdAndIsDeletedFalse(owner.getId());

        List<PetResponseDTO> petResponseDTOS = pets.stream().map(pet -> {
            Breed breed = breedRepository.findById(pet.getBreed().getId())
                    .orElseThrow(() -> new CustomException(ErrorCode.BREED_NOT_FOUND));

            BreedResponseDTO breedResponseDTO = BreedResponseDTO.builder()
                    .id(breed.getId())
                    .name(breed.getName())
                    .size(breed.getSize())
                    .build();

            List<DiseaseResponseDTO> diseases = pet.getPetDiseases().stream().map(PetDisease::getDisease).toList()
                    .stream().map(disease -> DiseaseResponseDTO.builder()
                            .id(disease.getId())
                            .name(disease.getName())
                            .build()
                    ).toList();

            return PetResponseDTO.builder()
                    .id(pet.getId())
                    .name(pet.getName())
                    .age(pet.getAge())
                    .breed(breedResponseDTO)
                    .gender(pet.getGender())
                    .diseases(diseases)
                    .build();
            }
        ).toList();
        return PetListResponseDTO.builder().pets(petResponseDTOS).build();
    }

    @Transactional
    public void createPet(String username, PetCreateRequestDTO dto) {
        User owner = userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        if (petRepository.countAllByOwnerIdAndIsDeletedFalse(owner.getId()).orElse(0) >= 2) {
            throw new CustomException(ErrorCode.MAXIMUM_PET_COUNT);
        }

        Breed breed = breedRepository.findById(dto.getBreedId())
                .orElseThrow(() -> new CustomException(ErrorCode.BREED_NOT_FOUND));

        Pet pet = Pet.builder()
                .name(dto.getName())
                .age(dto.getAge())
                .breed(breed)
                .gender(dto.getGender())
                .owner(owner)
                .isDeleted(false)
                .build();
        petRepository.save(pet);

        // pets-diseases n:m 저장
        List<Disease> diseases = diseaseRepository.findAllByIdIn(dto.getDiseaseIds());
        savePetDisease(pet, diseases);
    }

    @Transactional
    public void updatePet(String username, Long petId, PetUpdateRequestDTO dto) {
        User owner = userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        Pet pet = petRepository.findByIdAndIsDeletedFalse(petId)
                .orElseThrow(() -> new CustomException(ErrorCode.PET_NOT_FOUND));

        if (!pet.getOwner().getId().equals(owner.getId())) {
            throw new CustomException(ErrorCode.FORBIDDEN_PET_UPDATE);
        }

        Breed breed = breedRepository.findById(dto.getBreedId())
                .orElseThrow(() -> new CustomException(ErrorCode.BREED_NOT_FOUND));
        pet.update(dto.getName(), dto.getAge(), breed, dto.getGender());

        // pet disease n:m 삭제 후 추가
        petDiseaseRepository.deleteAllByPetId(petId);
        List<Disease> diseases = diseaseRepository.findAllByIdIn(dto.getDiseaseIds());
        savePetDisease(pet, diseases);
    }

    @Transactional
    public void deletePet(String username, Long petId) {
        User owner = userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        Pet pet = petRepository.findByIdAndIsDeletedFalse(petId)
                .orElseThrow(() -> new CustomException(ErrorCode.PET_NOT_FOUND));

        if (!pet.getOwner().getId().equals(owner.getId())) {
            throw new CustomException(ErrorCode.FORBIDDEN_PET_DELETE);
        }
        pet.delete();
    }
}
