package com.wagyu.wagyu_back.domain.disease.repository;

import com.wagyu.wagyu_back.domain.disease.entity.Disease;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DiseaseRepository extends JpaRepository<Disease, Long> {
    List<Disease> findAllByIdIn(List<Long> ids);
}
