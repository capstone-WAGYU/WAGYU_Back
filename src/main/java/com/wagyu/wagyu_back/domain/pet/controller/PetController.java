package com.wagyu.wagyu_back.domain.pet.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wagyu.wagyu_back.domain.pet.dto.request.PetCreateRequestDTO;
import com.wagyu.wagyu_back.domain.pet.dto.response.PetListResponseDTO;
import com.wagyu.wagyu_back.domain.pet.dto.request.PetUpdateRequestDTO;
import com.wagyu.wagyu_back.domain.pet.service.PetService;
import com.wagyu.wagyu_back.global.dto.ApiResponse;
import com.wagyu.wagyu_back.global.exception.CustomException;
import com.wagyu.wagyu_back.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/pet")
@RequiredArgsConstructor
public class PetController {
    private final PetService petService;
    private final ObjectMapper objectMapper;

    @GetMapping
    public ResponseEntity<ApiResponse<PetListResponseDTO>> getPets(Authentication authentication) {
        return ResponseEntity.ok(ApiResponse.success(petService.getPets(authentication.getName())));
    }

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ApiResponse<Void>> createPet(
            Authentication authentication,
            @RequestPart("data") String dataJson,
            @RequestPart(value = "image", required = false) MultipartFile image
    ) {
        PetCreateRequestDTO dto = parse(dataJson, PetCreateRequestDTO.class);
        petService.createPet(authentication.getName(), dto, image);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("반려동물이 등록되었습니다."));
    }

    @PutMapping(value = "{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ApiResponse<Void>> updatePet(
            Authentication authentication,
            @PathVariable Long id,
            @RequestPart("data") String dataJson,
            @RequestPart(value = "image", required = false) MultipartFile image
    ) {
        PetUpdateRequestDTO dto = parse(dataJson, PetUpdateRequestDTO.class);
        petService.updatePet(authentication.getName(), id, dto, image);
        return ResponseEntity.ok(ApiResponse.success("반려동물 정보가 수정되었습니다."));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse<Void>> deletePet(Authentication authentication, @PathVariable Long id) {
        petService.deletePet(authentication.getName(), id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    private <T> T parse(String json, Class<T> type) {
        try {
            return objectMapper.readValue(json, type);
        } catch (JsonProcessingException e) {
            throw new CustomException(ErrorCode.INVALID_REQUEST);
        }
    }
}
