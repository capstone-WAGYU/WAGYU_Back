package com.wagyu.wagyu_back.domain.reservation.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReservationDetailPetResponseDTO {
    private Long id;
    private String name;
}
