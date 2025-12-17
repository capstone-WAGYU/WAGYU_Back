package com.wagyu.wagyu_back.domain.hospital.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Entity
@Table(name = "hospital_schedules")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HospitalSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "hospital_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Hospital hospital;

    @Column(nullable = false, name = "day_of_week")
    private Short dayOfWeek;

    @Column(name = "open_time")
    private LocalTime openTime;

    @Column(name = "close_time")
    private LocalTime closeTime;

    @Column(nullable = false, name = "is_closed")
    private boolean isClosed;

    public void update(LocalTime open, LocalTime close, Boolean isClosed) {
        this.openTime = open;
        this.closeTime = close;
        this.isClosed = isClosed;
    }
}
