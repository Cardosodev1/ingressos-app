package com.ingressosapp.reservaservice.client.dto;

import java.time.LocalDateTime;
import java.util.List;

public record EventoDetalheDTO(
        String id,
        String titulo,
        Boolean ativo,
        String status,
        LocalDateTime dataHora,
        LocalDateTime dataAberturaVendas,
        List<SetorDTO> setores
) {}