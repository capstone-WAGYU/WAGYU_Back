package com.wagyu.wagyu_back.domain.pet.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class PetListResponseDTO {
    private List<PetResponseDTO> pets;
}
