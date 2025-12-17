package com.wagyu.wagyu_back.domain.hospital.dto.request;

import lombok.Getter;

import java.time.LocalTime;

@Getter
public class HospitalScheduleUpdateRequestDTO {
    private Short dayOfWeek;
    private LocalTime openTime;
    private LocalTime closeTime;
    private Boolean isClosed;
}
