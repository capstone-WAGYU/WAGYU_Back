package com.wagyu.wagyu_back.domain.hospital.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class HospitalSummaryListResponseDTO {
    private List<HospitalSummaryResponseDTO> hospitals;
}
