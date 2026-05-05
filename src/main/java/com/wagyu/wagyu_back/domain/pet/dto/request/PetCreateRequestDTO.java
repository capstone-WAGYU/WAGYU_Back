package com.wagyu.wagyu_back.domain.pet.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PetCreateRequestDTO {
    @NotBlank
    private String name;

    @NotNull
    private Short age;

    @NotNull
    private Long breedId;

    @NotNull
    private Character gender;

    @NotNull
    private List<Long> diseaseIds;
}
