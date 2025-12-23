package com.wagyu.wagyu_back.domain.hospital.service;

import com.wagyu.wagyu_back.domain.hospital.dto.request.HospitalCreateReservationRequestDTO;
import com.wagyu.wagyu_back.domain.hospital.dto.request.HospitalScheduleUpdateRequestDTO;
import com.wagyu.wagyu_back.domain.hospital.dto.request.HospitalUpdateRequestDTO;
import com.wagyu.wagyu_back.domain.hospital.dto.response.*;
import com.wagyu.wagyu_back.domain.hospital.entity.Hospital;
import com.wagyu.wagyu_back.domain.hospital.entity.HospitalAmenity;
import com.wagyu.wagyu_back.domain.hospital.entity.HospitalSchedule;
import com.wagyu.wagyu_back.domain.hospital.entity.HospitalScheduleException;
import com.wagyu.wagyu_back.domain.hospital.repository.HospitalAmenityRepository;
import com.wagyu.wagyu_back.domain.hospital.repository.HospitalRepository;
import com.wagyu.wagyu_back.domain.hospital.repository.HospitalScheduleExceptionRepository;
import com.wagyu.wagyu_back.domain.hospital.repository.HospitalScheduleRepository;
import com.wagyu.wagyu_back.domain.pet.entity.Pet;
import com.wagyu.wagyu_back.domain.pet.repository.PetRepository;
import com.wagyu.wagyu_back.domain.reservation.entity.Reservation;
import com.wagyu.wagyu_back.domain.reservation.enums.ReservationStatus;
import com.wagyu.wagyu_back.domain.reservation.repository.ReservationRepository;
import com.wagyu.wagyu_back.domain.user.entity.User;
import com.wagyu.wagyu_back.domain.user.repository.UserRepository;
import com.wagyu.wagyu_back.global.exception.CustomException;
import com.wagyu.wagyu_back.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HospitalService {
    private final HospitalRepository hospitalRepository;
    private final HospitalScheduleRepository hospitalScheduleRepository;
    private final HospitalScheduleExceptionRepository hospitalScheduleExceptionRepository;
    private final HospitalAmenityRepository hospitalAmenityRepository;

    private final UserRepository userRepository;
    private final PetRepository petRepository;
    private final ReservationRepository reservationRepository;

    private List<HospitalSummaryResponseDTO> convertHospitals(List<Hospital> hospitals) {
        return hospitals.stream().map(hospital -> {

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
    }

    // 전체 병원 리스트 조회
    @Transactional(readOnly = true)
    public Page<HospitalSummaryResponseDTO> getAllHospitals(Pageable pageable) {
        Page<Hospital> hospitals = hospitalRepository.findAll(pageable);
        List<HospitalSummaryResponseDTO> hospitalSummaryResponseDTOS = convertHospitals(hospitals.getContent());
        return new PageImpl<>(hospitalSummaryResponseDTOS, pageable, hospitals.getTotalElements());
    }

    @Transactional(readOnly = true)
    public HospitalSummaryListResponseDTO searchHospital(String name) {
        List<Hospital> hospitals = hospitalRepository.searchByName(name);
        List<HospitalSummaryResponseDTO> hospitalSummaryResponseDTOS = convertHospitals(hospitals);
        return HospitalSummaryListResponseDTO.builder()
                .hospitals(hospitalSummaryResponseDTOS)
                .build();
    }

    // 병원 정보 상세 조회
    @Transactional(readOnly = true)
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

        List<HospitalAmenity> amenities = hospitalAmenityRepository.findAllByHospitalId(hospitalId);
        List<HospitalDetailAmenityResponseDTO> amenityResponseDTOS = amenities.stream()
                .map(a -> HospitalDetailAmenityResponseDTO.builder()
                        .amenityCode(a.getAmenityCode())
                        .build()
                ).toList();

        return HospitalDetailResponseDTO.builder()
                .name(hospital.getName())
                .address(hospital.getAddress())
                .is24Hours(hospital.getIs24Hours())
                .schedules(scheduleDTOs)
                .scheduleExceptions(scheduleExceptionDTOs)
                .amenities(amenityResponseDTOS)
                .build();
    }

    // 병원 스케줄 조회
    @Transactional(readOnly = true)
    public HospitalScheduleResponseDTO getHospitalSchedule(Long hospitalId, LocalDate date) {
        Hospital hospital = hospitalRepository.findById(hospitalId)
                .orElseThrow(() -> new CustomException(ErrorCode.HOSPITAL_NOT_FOUND));

        Optional<HospitalScheduleException> optExSchedule =  hospitalScheduleExceptionRepository.findByHospitalIdAndDate(hospitalId, date);
        if  (optExSchedule.isPresent()) {
            HospitalScheduleException exSchedule = optExSchedule.get();
            return HospitalScheduleResponseDTO.builder()
                    .openTime(exSchedule.getOpenTime())
                    .closeTime(exSchedule.getCloseTime())
                    .isClosed(exSchedule.isClosed() || LocalTime.now().isAfter(exSchedule.getCloseTime()))
                    .build();
        }

        HospitalSchedule schedule = hospitalScheduleRepository
                .findByHospitalIdAndDayOfWeek(hospitalId, (short) date.getDayOfWeek().getValue())
                .orElseThrow(() -> new CustomException(ErrorCode.HOSPITAL_NO_SCHEDULE));

        return HospitalScheduleResponseDTO.builder()
                .openTime(schedule.getOpenTime())
                .closeTime(schedule.getCloseTime())
                .isClosed(schedule.isClosed() || LocalTime.now().isAfter(schedule.getCloseTime()))
                .build();
    }

    // 병원 정보 수정
    @Transactional
    public void updateHospital(String username, Long id, HospitalUpdateRequestDTO dto) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        Hospital hospital = hospitalRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.HOSPITAL_NOT_FOUND));

        if (!user.getId().equals(hospital.getOwner().getId())) {
            throw new CustomException(ErrorCode.FORBIDDEN_UPDATE_HOSPITAL);
        }

        hospital.update(dto.getName(), dto.getAddress(), dto.getIs24Hours());

        Map<Short, HospitalScheduleUpdateRequestDTO> scheduleMap = dto.getSchedules().stream()
                .collect(Collectors.toMap(
                        HospitalScheduleUpdateRequestDTO::getDayOfWeek,
                        Function.identity()
                ));
        hospitalScheduleRepository.findAllByHospitalId(id)
                .forEach(schedule -> {
                    var scheduleDTO = scheduleMap.get(schedule.getDayOfWeek());
                    if (scheduleDTO != null) {
                        schedule.update(
                                scheduleDTO.getOpenTime(),
                                scheduleDTO.getCloseTime(),
                                scheduleDTO.getIsClosed()
                        );
                    }
                });

        hospitalAmenityRepository.findAllByHospitalId(id);
        List<HospitalAmenity> amenities = dto.getAmenities().stream()
                .map(a -> HospitalAmenity.builder()
                        .hospital(hospital)
                        .amenityCode(a.getAmenityCode())
                        .build()
                ).toList();
        hospitalAmenityRepository.saveAll(amenities);
    }

    // 병원 예약
    @Transactional
    public void createHospitalReservation(String username, Long id, HospitalCreateReservationRequestDTO dto) {
        Hospital hospital = hospitalRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.HOSPITAL_NOT_FOUND));

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        Pet pet = petRepository.findByIdAndIsDeletedFalse(dto.getPetId())
                .orElseThrow(() -> new CustomException(ErrorCode.PET_NOT_FOUND));

        Reservation reservation = Reservation.builder()
                .user(user)
                .pet(pet)
                .hospital(hospital)
                .date(dto.getDate())
                .time(dto.getTime())
                .reason(dto.getReason())
                .comment(dto.getComment())
                .status(ReservationStatus.PENDING)
                .build();
        reservationRepository.save(reservation);
    }
}
