package com.wagyu.wagyu_back.domain.hospital.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class HospitalDetailScheduleExceptionResponseDTO {
    private LocalDate date;
    private Boolean isClosed;
}
