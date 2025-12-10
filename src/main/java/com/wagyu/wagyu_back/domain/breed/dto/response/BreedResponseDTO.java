package com.wagyu.wagyu_back.domain.breed.dto.response;

import com.wagyu.wagyu_back.domain.breed.enums.BreedSize;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BreedResponseDTO {
    private Long id;
    private String name;
    private BreedSize size;
}
