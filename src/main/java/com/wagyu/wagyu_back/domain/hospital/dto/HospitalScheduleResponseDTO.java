package com.wagyu.wagyu_back.domain.hospital.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalTime;

@Getter
@Builder
public class HospitalScheduleResponseDTO {
    private LocalTime openTime;
    private LocalTime closeTime;
    private Boolean isClosed;
}
