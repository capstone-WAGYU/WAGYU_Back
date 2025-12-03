package com.wagyu.wagyu_back.domain.hospital.controller;

import com.wagyu.wagyu_back.domain.hospital.dto.HospitalDetailResponseDTO;
import com.wagyu.wagyu_back.domain.hospital.dto.HospitalSummaryResponseDTO;
import com.wagyu.wagyu_back.domain.hospital.service.HospitalService;
import com.wagyu.wagyu_back.global.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/hospital")
@RequiredArgsConstructor
public class HospitalController {
    private final HospitalService hospitalService;

    @GetMapping
    public ResponseEntity<ApiResponse<Page<HospitalSummaryResponseDTO>>> getAllHospitals(Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(hospitalService.getAllHospitals(pageable)));
    }

    @GetMapping("{id}")
    public ResponseEntity<ApiResponse<HospitalDetailResponseDTO>> getHospitalDetail(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(hospitalService.getHospitalDetail(id)));
    }
}
