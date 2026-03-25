package com.ingressosapp.catalogoservice.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.List;

public record SetorRequestDTO(
        @NotBlank(message = "O nome do setor é obrigatório")
        String nome,

        @NotNull(message = "A capacidade total é obrigatória")
        @Positive(message = "A capacidade deve ser maior que zero")
        Integer capacidadeTotal,

        @NotEmpty(message = "O setor deve ter pelo menos um preço configurado")
        @Valid
        List<PrecoRequestDTO> precos
) {}