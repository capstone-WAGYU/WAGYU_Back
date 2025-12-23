package com.wagyu.wagyu_back.domain.hospital.controller;

import com.wagyu.wagyu_back.domain.hospital.dto.request.HospitalUpdateRequestDTO;
import com.wagyu.wagyu_back.domain.hospital.dto.response.HospitalDetailResponseDTO;
import com.wagyu.wagyu_back.domain.hospital.dto.response.HospitalScheduleResponseDTO;
import com.wagyu.wagyu_back.domain.hospital.dto.response.HospitalSummaryListResponseDTO;
import com.wagyu.wagyu_back.domain.hospital.dto.response.HospitalSummaryResponseDTO;
import com.wagyu.wagyu_back.domain.hospital.service.HospitalService;
import com.wagyu.wagyu_back.global.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("api/hospital")
@RequiredArgsConstructor
public class HospitalController {
    private final HospitalService hospitalService;

    @GetMapping
    public ResponseEntity<ApiResponse<Page<HospitalSummaryResponseDTO>>> getAllHospitals(Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(hospitalService.getAllHospitals(pageable)));
    }

    @GetMapping("search")
    public ResponseEntity<ApiResponse<HospitalSummaryListResponseDTO>> searchHospital(
            @RequestParam String name
    ) {
        return ResponseEntity.ok(ApiResponse.success(hospitalService.searchHospital(name)));
    }

    @GetMapping("{id}")
    public ResponseEntity<ApiResponse<HospitalDetailResponseDTO>> getHospitalDetail(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(hospitalService.getHospitalDetail(id)));
    }

    @GetMapping("{id}/schedule")
    public ResponseEntity<ApiResponse<HospitalScheduleResponseDTO>> getHospitalSchedule(
            @PathVariable Long id,
            @RequestParam(name = "date")LocalDate date
    ) {
        return ResponseEntity.ok(ApiResponse.success(hospitalService.getHospitalSchedule(id, date)));
    }

    @PutMapping("{id}")
    public ResponseEntity<ApiResponse<Void>> updateHospital(
            Authentication authentication,
            @PathVariable Long id,
            @RequestBody HospitalUpdateRequestDTO hospitalUpdateRequestDTO
    ) {
        hospitalService.updateHospital(authentication.getName(), id, hospitalUpdateRequestDTO);
        return ResponseEntity.ok(ApiResponse.success());
    }
}
