package com.wagyu.wagyu_back.domain.disease.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class DiseaseListResponseDTO {
    private List<DiseaseResponseDTO> diseases;
}
