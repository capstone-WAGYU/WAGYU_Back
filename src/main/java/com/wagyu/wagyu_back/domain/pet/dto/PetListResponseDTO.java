package com.wagyu.wagyu_back.domain.pet.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class PetListResponseDTO {
    private List<PetResponseDTO> pets;
}
