package com.wagyu.wagyu_back.domain.reservation.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReservationDetailHospitalResponseDTO {
    private Long id;
    private String name;
}
