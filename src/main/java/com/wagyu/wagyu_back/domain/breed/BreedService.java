package com.wagyu.wagyu_back.domain.breed;

import com.wagyu.wagyu_back.domain.breed.dto.response.BreedListResponseDTO;
import com.wagyu.wagyu_back.domain.breed.dto.response.BreedResponseDTO;
import com.wagyu.wagyu_back.domain.breed.entity.Breed;
import com.wagyu.wagyu_back.domain.breed.repository.BreedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BreedService {
    private final BreedRepository breedRepository;

    public BreedListResponseDTO getBreedList() {
        List<Breed> breeds = breedRepository.findAll();
        List<BreedResponseDTO> breedResponseDTOS = breeds.stream().map(breed -> BreedResponseDTO.builder()
                .id(breed.getId())
                .name(breed.getName())
                .size(breed.getSize())
                .build()
        ).toList();

        return BreedListResponseDTO.builder().breeds(breedResponseDTOS).build();
    }
}
