package com.ingressosapp.catalogoservice.dto.response;

import com.ingressosapp.catalogoservice.dto.request.PrecoRequestDTO;

import java.util.List;

public record LoteResponseDTO(
        String id,
        String nome,
        Integer capacidade,
        Integer ingressosVendidos,
        Boolean ativo,
        List<PrecoRequestDTO> precos
) {
}
