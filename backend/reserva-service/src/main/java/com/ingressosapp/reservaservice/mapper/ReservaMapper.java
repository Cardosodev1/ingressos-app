package com.ingressosapp.reservaservice.mapper;

import com.ingressosapp.reservaservice.domain.ItemReserva;
import com.ingressosapp.reservaservice.domain.Reserva;
import com.ingressosapp.reservaservice.domain.enums.StatusReserva;
import com.ingressosapp.reservaservice.dto.request.ItemReservaRequestDTO;
import com.ingressosapp.reservaservice.dto.response.ReservaResponseDTO;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
public class ReservaMapper {

    // --- 1. Mapeamento de Entrada (Request -> Entidade) ---

    public Reserva toEntity(String usuarioId, LocalDateTime dataExpiracao) {
        return Reserva.builder()
                .usuarioId(usuarioId)
                .status(StatusReserva.PENDENTE)
                .valorTotal(BigDecimal.ZERO)
                .dataExpiracao(dataExpiracao)
                .build();
    }

    public ItemReserva toItemEntity(ItemReservaRequestDTO dto) {
        if (dto == null) return null;

        return ItemReserva.builder()
                .eventoId(dto.eventoId())
                .setorId(dto.setorId())
                .loteId(dto.loteId())
                .tipoIngresso(dto.tipoIngresso())
                .quantidade(dto.quantidade())
                .build();
    }

    // --- 2. Mapeamento de Saída (Entidade -> Response) ---

    public ReservaResponseDTO toResponseDTO(Reserva reserva) {
        if (reserva == null) return null;

        return new ReservaResponseDTO(
                reserva.getId(),
                reserva.getStatus(),
                reserva.getValorTotal(),
                reserva.getDataExpiracao()
        );
    }
}