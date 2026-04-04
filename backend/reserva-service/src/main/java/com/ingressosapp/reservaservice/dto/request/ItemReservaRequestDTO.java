package com.ingressosapp.reservaservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ItemReservaRequestDTO(
        @NotBlank(message = "O ID do evento é obrigatório")
        String eventoId,

        @NotBlank(message = "O ID do setor é obrigatório")
        String setorId,

        @NotBlank(message = "O ID do lote é obrigatório")
        String loteId,

        @NotBlank(message = "O tipo de ingresso é obrigatório")
        String tipoIngresso,

        @NotNull(message = "A quantidade é obrigatória")
        @Positive(message = "A quantidade deve ser maior que zero")
        Integer quantidade
) {}