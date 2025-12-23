package com.wagyu.wagyu_back.domain.hospital.repository;

import com.wagyu.wagyu_back.domain.hospital.entity.Hospital;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HospitalRepository extends CrudRepository<Hospital, Long> {
    Page<Hospital> findAll(Pageable pageable);

    @Query(value = "SELECT * FROM hospitals WHERE MATCH(name) AGAINST(:name IN NATURAL LANGUAGE MODE)", nativeQuery = true)
    List<Hospital> searchByName(@Param("name") String name);
}
