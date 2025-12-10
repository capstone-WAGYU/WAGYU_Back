package com.wagyu.wagyu_back.domain.pet.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.util.List;

@Getter
public class PetCreateRequestDTO {
    @NotBlank
    private String name;

    @NotBlank
    private Short age;

    @NotBlank
    private Long breedId;

    @NotBlank
    private Character gender;

    @NotBlank
    private List<Long> diseaseIds;
}
