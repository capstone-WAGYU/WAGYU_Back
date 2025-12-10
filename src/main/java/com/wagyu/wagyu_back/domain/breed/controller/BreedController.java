package com.wagyu.wagyu_back.domain.breed.controller;

import com.wagyu.wagyu_back.domain.breed.BreedService;
import com.wagyu.wagyu_back.domain.breed.dto.response.BreedListResponseDTO;
import com.wagyu.wagyu_back.global.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/breed")
@RequiredArgsConstructor
public class BreedController {
    private final BreedService breedService;

    @GetMapping
    public ResponseEntity<ApiResponse<BreedListResponseDTO>> getBreedList() {
        return ResponseEntity.ok(ApiResponse.success(breedService.getBreedList()));
    }
}
