package com.wagyu.wagyu_back.domain.reservation.dto.response;

import com.wagyu.wagyu_back.domain.reservation.enums.ReservationStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Builder
public class ReservationDetailResponseDTO {
    private Long id;
    private ReservationDetailPetResponseDTO pet;
    private ReservationDetailHospitalResponseDTO hospital;
    private LocalDate date;
    private LocalTime time;
    private String reason;
    private String comment;
    private ReservationStatus status;
    private String hospitalComment;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
