package com.wagyu.wagyu_back.domain.reservation.controller;

import com.wagyu.wagyu_back.domain.auth.enums.AuthLevel;
import com.wagyu.wagyu_back.domain.reservation.dto.response.ReservationDetailResponseDTO;
import com.wagyu.wagyu_back.domain.reservation.dto.response.ReservationSummaryListResponseDTO;
import com.wagyu.wagyu_back.domain.reservation.service.ReservationService;
import com.wagyu.wagyu_back.global.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/reservation")
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;

    @GetMapping
    public ResponseEntity<ApiResponse<ReservationSummaryListResponseDTO>> getReservations(
            Authentication authentication
    ) {
        return ResponseEntity.ok(
                ApiResponse.success(reservationService.getReservations(
                        authentication.getName(), authentication.getAuthorities()
                                .stream()
                                .findFirst()
                                .map(a -> AuthLevel.valueOf(a.getAuthority()))
                                .orElseThrow()
                ))
        );
    }

    @GetMapping("{id}")
    public ResponseEntity<ApiResponse<ReservationDetailResponseDTO>> getReservationDetail(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(ApiResponse.success(reservationService.getReservationDetail(id)));
    }
}
