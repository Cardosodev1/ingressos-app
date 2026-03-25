package com.ingressosapp.catalogoservice.dto.response;

import com.ingressosapp.catalogoservice.domain.enums.Categoria;
import com.ingressosapp.catalogoservice.domain.enums.StatusEvento;
import com.ingressosapp.catalogoservice.dto.request.EnderecoDTO;
import java.time.LocalDateTime;
import java.util.List;

public record EventoDetalheDTO(
        String id,
        String titulo,
        String descricao,
        String imagemUrl,
        Categoria categoria,
        StatusEvento status,
        LocalDateTime dataHora,
        LocalDateTime dataHoraFim,
        LocalDateTime dataAberturaVendas,
        EnderecoDTO local,
        String produtora,
        Integer classificacaoEtaria,
        List<SetorResponseDTO> setores
) {}