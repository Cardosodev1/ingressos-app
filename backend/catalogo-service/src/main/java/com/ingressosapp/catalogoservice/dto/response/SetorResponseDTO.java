package com.ingressosapp.catalogoservice.dto.response;

import java.util.List;

public record SetorResponseDTO(
        String id,
        String nome,
        Integer capacidadeTotal,
        Integer ingressosDisponiveis,
        List<LoteResponseDTO> lotes
) {}