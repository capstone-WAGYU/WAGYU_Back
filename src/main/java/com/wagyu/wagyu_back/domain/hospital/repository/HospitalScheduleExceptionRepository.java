package com.wagyu.wagyu_back.domain.hospital.repository;

import com.wagyu.wagyu_back.domain.hospital.entity.HospitalScheduleException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface HospitalScheduleExceptionRepository extends JpaRepository<HospitalScheduleException, Long> {
    Optional<HospitalScheduleException> findByHospitalIdAndDate(Long hospitalId, LocalDate date);

    List<HospitalScheduleException> findAllByHospitalId(Long hospitalId);
}
