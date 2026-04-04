package com.ingressosapp.reservaservice.client.dto;

import java.util.List;

public record SetorDTO(
        String id,
        String nome,
        Integer capacidadeTotal,
        Integer ingressosDisponiveis,
        List<LoteDTO> lotes
) {}