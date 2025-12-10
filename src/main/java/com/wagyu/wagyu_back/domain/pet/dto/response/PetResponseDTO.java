package com.wagyu.wagyu_back.domain.pet.dto.response;

import com.wagyu.wagyu_back.domain.breed.dto.response.BreedResponseDTO;
import com.wagyu.wagyu_back.domain.disease.dto.response.DiseaseResponseDTO;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class PetResponseDTO {
    private Long id;

    private String name;

    private Short age;

    private BreedResponseDTO breed;

    private Character gender;

    private List<DiseaseResponseDTO> diseases;
}
