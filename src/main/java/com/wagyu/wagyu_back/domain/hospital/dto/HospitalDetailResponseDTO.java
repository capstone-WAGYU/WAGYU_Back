package com.wagyu.wagyu_back.domain.hospital.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalTime;
import java.util.List;

@Getter
@Builder
public class HospitalDetailResponseDTO {
    private String name;
    private String address;
    private List<HospitalDetailScheduleResponseDTO> schedules;
    private List<HospitalDetailScheduleExceptionResponseDTO> scheduleExceptions;
}
