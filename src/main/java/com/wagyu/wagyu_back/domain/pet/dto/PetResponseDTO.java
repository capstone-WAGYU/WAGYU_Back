package com.wagyu.wagyu_back.domain.pet.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PetResponseDTO {
    private Long id;

    private String name;

    private Short age;

    private String breed;

    private Character gender;
}
