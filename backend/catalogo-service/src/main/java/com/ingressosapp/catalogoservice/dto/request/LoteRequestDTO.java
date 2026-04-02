package com.ingressosapp.catalogoservice.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.List;

public record LoteRequestDTO(
        @NotBlank(message = "O nome do lote é obrigatório")
        String nome,

        @NotNull(message = "A capacidade do lote é obrigatória")
        @Positive(message = "A capacidade deve ser maior que zero")
        Integer capacidade,

        @NotEmpty(message = "O lote deve ter pelo menos um preço configurado")
        @Valid
        List<PrecoRequestDTO> precos
) {}