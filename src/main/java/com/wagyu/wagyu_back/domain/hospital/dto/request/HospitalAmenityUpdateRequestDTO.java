package com.wagyu.wagyu_back.domain.hospital.dto.request;

import com.wagyu.wagyu_back.domain.hospital.enums.AmenityCode;
import lombok.Getter;

@Getter
public class HospitalAmenityUpdateRequestDTO {
    private AmenityCode amenityCode;
}
