package com.wagyu.wagyu_back.domain.hospital.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class HospitalDetailScheduleResponseDTO {
    private Short dayOfWeek;
    private Boolean isClosed;
}
