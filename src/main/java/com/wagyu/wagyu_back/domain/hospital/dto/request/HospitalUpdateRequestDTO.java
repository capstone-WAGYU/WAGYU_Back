package com.wagyu.wagyu_back.domain.hospital.dto.request;

import lombok.Getter;

import java.util.List;

@Getter
public class HospitalUpdateRequestDTO {
    private String name;
    private String address;
    private Boolean is24Hours;
    private List<HospitalScheduleUpdateRequestDTO> schedules;
    private List<HospitalAmenityUpdateRequestDTO> amenities;
}
