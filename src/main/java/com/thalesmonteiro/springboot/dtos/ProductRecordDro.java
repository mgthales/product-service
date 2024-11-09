package com.thalesmonteiro.springboot.dtos;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProductRecordDro(@NotBlank String name, @NotNull BigDecimal value) {

}
