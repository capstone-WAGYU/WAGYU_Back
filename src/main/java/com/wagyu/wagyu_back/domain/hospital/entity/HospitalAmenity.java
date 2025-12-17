package com.wagyu.wagyu_back.domain.hospital.entity;

import com.wagyu.wagyu_back.domain.hospital.enums.AmenityCode;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "hospital_amenities")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HospitalAmenity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "hospital_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Hospital hospital;

    @Column(name = "amenity_code")
    @Enumerated(EnumType.STRING)
    private AmenityCode amenityCode;
}
