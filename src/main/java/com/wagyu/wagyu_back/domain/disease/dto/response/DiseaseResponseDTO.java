package com.wagyu.wagyu_back.domain.disease.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DiseaseResponseDTO {
    private Long id;
    private String name;
}
