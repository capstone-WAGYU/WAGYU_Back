package com.wagyu.wagyu_back.domain.hospital.dto.request;

import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
public class HospitalCreateReservationRequestDTO {
    private Long petId;
    private LocalDate date;
    private LocalTime time;
    private String reason;
    private String comment;
}
