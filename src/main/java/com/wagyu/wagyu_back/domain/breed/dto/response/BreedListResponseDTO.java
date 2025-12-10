package com.wagyu.wagyu_back.domain.breed.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class BreedListResponseDTO {
    private List<BreedResponseDTO> breeds;
}
