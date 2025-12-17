package com.wagyu.wagyu_back.domain.disease.controller;

import com.wagyu.wagyu_back.domain.disease.dto.response.DiseaseListResponseDTO;
import com.wagyu.wagyu_back.domain.disease.service.DiseaseService;
import com.wagyu.wagyu_back.global.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/disease")
@RequiredArgsConstructor
public class DiseaseController {
    private final DiseaseService diseaseService;

    @GetMapping("{id}")
    public ResponseEntity<ApiResponse<DiseaseListResponseDTO>> getDiseases(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(diseaseService.getDiseases(id)));
    }
}
