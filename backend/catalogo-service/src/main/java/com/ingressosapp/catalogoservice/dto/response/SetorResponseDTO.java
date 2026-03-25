package com.ingressosapp.catalogoservice.dto.response;

import com.ingressosapp.catalogoservice.dto.request.PrecoRequestDTO;
import java.util.List;

public record SetorResponseDTO(
        String id,
        String nome,
        Integer capacidadeTotal,
        Integer ingressosDisponiveis,
        List<PrecoRequestDTO> precos
) {}