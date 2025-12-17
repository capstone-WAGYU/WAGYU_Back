package com.wagyu.wagyu_back.domain.hospital.dto.response;

import com.wagyu.wagyu_back.domain.hospital.enums.AmenityCode;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class HospitalDetailAmenityResponseDTO {
    private AmenityCode amenityCode;
}
