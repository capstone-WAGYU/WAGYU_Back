package com.wagyu.wagyu_back.domain.disease.service;

import com.wagyu.wagyu_back.domain.breed.repository.BreedDiseaseRepository;
import com.wagyu.wagyu_back.domain.disease.dto.response.DiseaseListResponseDTO;
import com.wagyu.wagyu_back.domain.disease.dto.response.DiseaseResponseDTO;
import com.wagyu.wagyu_back.domain.disease.entity.Disease;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DiseaseService {
    private final BreedDiseaseRepository breedDiseaseRepository;

    public DiseaseListResponseDTO getDiseases(Long id) {
        List<DiseaseResponseDTO> diseases = breedDiseaseRepository.findDiseaseByBreedId(id).stream()
                .map(d -> DiseaseResponseDTO.builder()
                        .id(d.getId())
                        .name(d.getName())
                        .build()
                ).toList();

        return DiseaseListResponseDTO.builder()
                .diseases(diseases)
                .build();
    }
}
