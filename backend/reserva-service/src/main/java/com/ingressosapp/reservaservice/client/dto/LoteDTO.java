package com.ingressosapp.reservaservice.client.dto;

import java.util.List;

public record LoteDTO(
        String id,
        String nome,
        Boolean ativo,
        Integer capacidade,
        Integer ingressosVendidos,
        List<PrecoDTO> precos
) {
    public int getQuantidadeDisponivel() {
        return capacidade - (ingressosVendidos != null ? ingressosVendidos : 0);
    }
}