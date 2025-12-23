package com.wagyu.wagyu_back.domain.reservation.repository;

import com.wagyu.wagyu_back.domain.reservation.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findAllByUserIdOrderByCreatedAtDesc(Long userId);
    List<Reservation> findAllByHospitalIdOrderByCreatedAtDesc(Long hospitalId);
}
