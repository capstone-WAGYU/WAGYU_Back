package com.wagyu.wagyu_back.domain.reservation.service;

import com.wagyu.wagyu_back.domain.auth.enums.AuthLevel;
import com.wagyu.wagyu_back.domain.hospital.entity.Hospital;
import com.wagyu.wagyu_back.domain.hospital.repository.HospitalRepository;
import com.wagyu.wagyu_back.domain.reservation.dto.request.ReservationUpdateStatusRequestDTO;
import com.wagyu.wagyu_back.domain.reservation.dto.response.*;
import com.wagyu.wagyu_back.domain.reservation.entity.Reservation;
import com.wagyu.wagyu_back.domain.reservation.enums.ReservationStatus;
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
    private final HospitalRepository hospitalRepository;

    @Transactional(readOnly = true)
    public ReservationSummaryListResponseDTO getReservations(String username, AuthLevel authLevel) {
        User user = userRepository.findByUsernameAndIsDeletedFalse(username)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        List<Reservation> reservations;
        if (authLevel == AuthLevel.USER) {
            reservations = reservationRepository.findAllByUserIdOrderByCreatedAtDesc(user.getId());
        } else if (authLevel == AuthLevel.HOSPITAL) {
            Hospital hospital = hospitalRepository.findByOwnerId(user.getId());
            reservations = reservationRepository.findAllByHospitalIdOrderByCreatedAtDesc(hospital.getId());
        } else {
            throw new CustomException(ErrorCode.INVALID_REQUEST);
        }

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

    @Transactional
    public void updateReservationStatus(String username, Long id, ReservationUpdateStatusRequestDTO dto) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.RESERVATION_NOT_FOUND));

        User user = userRepository.findByUsernameAndIsDeletedFalse(username)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        User owner = reservation.getHospital().getOwner();
        if (owner == null || !user.getId().equals(owner.getId())) {
            throw new CustomException(ErrorCode.FORBIDDEN_UPDATE_RESERVATION);
        }

        if (dto.getStatus().equals(ReservationStatus.REJECTED)) {
            reservation.reject(dto.getStatus(), dto.getHospitalComment());
        } else {
            reservation.updateStatus(dto.getStatus());
        }
    }
}
