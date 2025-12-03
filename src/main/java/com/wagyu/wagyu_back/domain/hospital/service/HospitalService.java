package com.wagyu.wagyu_back.domain.hospital.service;

import com.wagyu.wagyu_back.domain.hospital.dto.HospitalDetailResponseDTO;
import com.wagyu.wagyu_back.domain.hospital.dto.HospitalDetailScheduleExceptionResponseDTO;
import com.wagyu.wagyu_back.domain.hospital.dto.HospitalDetailScheduleResponseDTO;
import com.wagyu.wagyu_back.domain.hospital.dto.HospitalSummaryResponseDTO;
import com.wagyu.wagyu_back.domain.hospital.entity.Hospital;
import com.wagyu.wagyu_back.domain.hospital.entity.HospitalSchedule;
import com.wagyu.wagyu_back.domain.hospital.entity.HospitalScheduleException;
import com.wagyu.wagyu_back.domain.hospital.repository.HospitalRepository;
import com.wagyu.wagyu_back.domain.hospital.repository.HospitalScheduleExceptionRepository;
import com.wagyu.wagyu_back.domain.hospital.repository.HospitalScheduleRepository;
import com.wagyu.wagyu_back.global.exception.CustomException;
import com.wagyu.wagyu_back.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HospitalService {
    private final HospitalRepository hospitalRepository;
    private final HospitalScheduleRepository hospitalScheduleRepository;
    private final HospitalScheduleExceptionRepository hospitalScheduleExceptionRepository;

    public Page<HospitalSummaryResponseDTO> getAllHospitals(Pageable pageable) {
        List<Hospital> hospitals = hospitalRepository.findAll(pageable);
        List<HospitalSummaryResponseDTO> hospitalSummaryResponseDTOS = hospitals.stream().map(hospital -> {

            Optional<HospitalScheduleException> optExSchedule = hospitalScheduleExceptionRepository
                    .findByHospitalIdAndDate(hospital.getId(), LocalDate.now());
            if (optExSchedule.isPresent()) {
                HospitalScheduleException exSchedule = optExSchedule.get();
                return HospitalSummaryResponseDTO.builder()
                        .id(hospital.getId())
                        .name(hospital.getName())
                        .address(hospital.getAddress())
                        .openTime(exSchedule.getOpenTime())
                        .closeTime(exSchedule.getCloseTime())
                        .isClosed(exSchedule.isClosed() || LocalTime.now().isAfter(exSchedule.getCloseTime()))
                        .build();
            }

            HospitalSchedule schedule = hospitalScheduleRepository
                    .findByHospitalIdAndDayOfWeek(hospital.getId(), (short) LocalDate.now().getDayOfWeek().getValue())
                    .orElseThrow(() -> new CustomException(ErrorCode.HOSPITAL_NO_SCHEDULE));
            return HospitalSummaryResponseDTO.builder()
                    .id(hospital.getId())
                    .name(hospital.getName())
                    .address(hospital.getAddress())
                    .openTime(schedule.getOpenTime())
                    .closeTime(schedule.getCloseTime())
                    .isClosed(schedule.isClosed() || LocalTime.now().isAfter(schedule.getCloseTime()))
                    .build();
        }).toList();
        return new PageImpl<>(hospitalSummaryResponseDTOS, pageable, hospitals.size());
    }

    public HospitalDetailResponseDTO getHospitalDetail(Long hospitalId) {
        Hospital hospital = hospitalRepository.findById(hospitalId)
                .orElseThrow(() -> new CustomException(ErrorCode.HOSPITAL_NOT_FOUND));

        List<HospitalSchedule> schedules = hospitalScheduleRepository.findAllByHospitalId(hospitalId);
        List<HospitalDetailScheduleResponseDTO> scheduleDTOs = schedules.stream()
                .map((s) -> HospitalDetailScheduleResponseDTO.builder()
                        .dayOfWeek(s.getDayOfWeek())
                        .isClosed(s.isClosed())
                        .build()
                ).toList();

        List<HospitalScheduleException> scheduleExceptions = hospitalScheduleExceptionRepository.findAllByHospitalId(hospitalId);
        List<HospitalDetailScheduleExceptionResponseDTO> scheduleExceptionDTOs = scheduleExceptions.stream()
                .map((s) -> HospitalDetailScheduleExceptionResponseDTO.builder()
                        .date(s.getDate())
                        .isClosed(s.isClosed())
                        .build()
                ).toList();

        return HospitalDetailResponseDTO.builder()
                .name(hospital.getName())
                .address(hospital.getAddress())
                .schedules(scheduleDTOs)
                .scheduleExceptions(scheduleExceptionDTOs)
                .build();
    }
}
