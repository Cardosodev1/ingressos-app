package com.ingressosapp.reservaservice.dto.response;

import com.ingressosapp.reservaservice.domain.enums.StatusReserva;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ReservaResponseDTO(
        String id,
        StatusReserva status,
        BigDecimal valorTotal,
        LocalDateTime dataExpiracao
) {}