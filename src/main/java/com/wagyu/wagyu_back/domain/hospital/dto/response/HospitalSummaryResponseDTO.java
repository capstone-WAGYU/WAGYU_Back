package com.wagyu.wagyu_back.domain.hospital.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalTime;

@Getter
@Builder
public class HospitalSummaryResponseDTO {
    private Long id;
    private String name;
    private String address;
    private LocalTime openTime;
    private LocalTime closeTime;
    private boolean isClosed;
}
