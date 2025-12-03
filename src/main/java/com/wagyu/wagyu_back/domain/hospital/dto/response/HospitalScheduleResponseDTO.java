package com.wagyu.wagyu_back.domain.hospital.dto.response;

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
