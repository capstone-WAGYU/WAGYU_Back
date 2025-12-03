package com.wagyu.wagyu_back.domain.hospital.repository;

import com.wagyu.wagyu_back.domain.hospital.entity.HospitalSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HospitalScheduleRepository extends JpaRepository<HospitalSchedule, Long> {
    Optional<HospitalSchedule> findByHospitalIdAndDayOfWeek(Long hospitalId, Short dayOffWeek);

    List<HospitalSchedule> findAllByHospitalId(Long hospitalId);
}
