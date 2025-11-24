package com.wagyu.wagyu_back.domain.pet.controller;

import com.wagyu.wagyu_back.domain.pet.dto.PetCreateRequestDTO;
import com.wagyu.wagyu_back.domain.pet.dto.PetListResponseDTO;
import com.wagyu.wagyu_back.domain.pet.dto.PetUpdateRequestDTO;
import com.wagyu.wagyu_back.domain.pet.service.PetService;
import com.wagyu.wagyu_back.global.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/pet")
@RequiredArgsConstructor
public class PetController {
    private final PetService petService;

    @GetMapping
    public ResponseEntity<ApiResponse<PetListResponseDTO>> getPets(Authentication authentication) {
        return ResponseEntity.ok(ApiResponse.success(petService.getPets(authentication.getName())));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> createPet(Authentication authentication, @RequestBody PetCreateRequestDTO dto) {
        petService.createPet(authentication.getName(), dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("반려동물이 등록되었습니다."));
    }

    @PutMapping("{id}")
    public ResponseEntity<ApiResponse<Void>> updatePet(@PathVariable Long id, @RequestBody PetUpdateRequestDTO dto) {
        petService.updatePet(id, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("반려동물 정보가 수정되었습니다."));
    }
}
