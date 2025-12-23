package com.wagyu.wagyu_back.domain.reservation.service;

import com.wagyu.wagyu_back.domain.reservation.dto.response.*;
import com.wagyu.wagyu_back.domain.reservation.entity.Reservation;
import com.wagyu.wagyu_back.domain.reservation.repository.ReservationRepository;
import com.wagyu.wagyu_back.domain.user.entity.User;
import com.wagyu.wagyu_back.domain.user.repository.UserRepository;
import com.wagyu.wagyu_back.global.exception.CustomException;
import com.wagyu.wagyu_back.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public ReservationSummaryListResponseDTO getReservations(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        List<Reservation> reservations = reservationRepository.findAllByUserIdOrderByCreatedAtDesc(user.getId());
        List<ReservationSummaryResponseDTO> summaries = reservations.stream()
                .map(r -> ReservationSummaryResponseDTO.builder()
                        .id(r.getId())
                        .hospitalName(r.getHospital().getName())
                        .petName(r.getPet().getName())
                        .date(r.getDate())
                        .time(r.getTime())
                        .status(r.getStatus())
                        .build()
                ).toList();
        return ReservationSummaryListResponseDTO.builder()
                .reservations(summaries)
                .build();
    }

    @Transactional(readOnly = true)
    public ReservationDetailResponseDTO getReservationDetail(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.RESERVATION_NOT_FOUND));

        return ReservationDetailResponseDTO.builder()
                .id(reservation.getId())
                .pet(ReservationDetailPetResponseDTO.builder()
                        .id(reservation.getPet().getId())
                        .name(reservation.getPet().getName())
                        .build()
                )
                .hospital(ReservationDetailHospitalResponseDTO.builder()
                        .id(reservation.getHospital().getId())
                        .name(reservation.getHospital().getName())
                        .build()
                )
                .date(reservation.getDate())
                .time(reservation.getTime())
                .reason(reservation.getReason())
                .comment(reservation.getComment())
                .status(reservation.getStatus())
                .hospitalComment(reservation.getHospitalComment())
                .createdAt(reservation.getCreatedAt())
                .updatedAt(reservation.getUpdatedAt())
                .build();
    }
}
