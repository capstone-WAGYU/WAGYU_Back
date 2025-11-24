package com.wagyu.wagyu_back.domain.pet.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class PetCreateRequestDTO {
    @NotBlank
    private String name;

    @NotBlank
    private Short age;

    @NotBlank
    private String breed;

    @NotBlank
    private Character gender;
}
