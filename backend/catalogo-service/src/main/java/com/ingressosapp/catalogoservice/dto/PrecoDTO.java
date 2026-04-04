package com.ingressosapp.catalogoservice.dto;

import com.ingressosapp.catalogoservice.domain.enums.TipoIngresso;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public record PrecoDTO(
        @NotNull(message = "O tipo de ingresso é obrigatório")
        TipoIngresso tipoIngresso,

        @NotNull(message = "O valor é obrigatório")
        @Positive(message = "O valor deve ser maior que zero")
        BigDecimal valor
) {}