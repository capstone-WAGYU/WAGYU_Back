package com.wagyu.wagyu_back.domain.hospital.repository;

import com.wagyu.wagyu_back.domain.hospital.entity.HospitalAmenity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HospitalAmenityRepository extends JpaRepository<HospitalAmenity, Long> {
    List<HospitalAmenity> findAllByHospitalId(Long hospitalId);
    void deleteAllByHospitalId(Long hospitalId);
}
