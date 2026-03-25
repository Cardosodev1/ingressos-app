package com.ingressosapp.catalogoservice.dto.response;

import com.ingressosapp.catalogoservice.domain.enums.Categoria;
import com.ingressosapp.catalogoservice.domain.enums.StatusEvento;
import java.time.LocalDateTime;

public record EventoResumoDTO(
        String id,
        String titulo,
        Categoria categoria,
        StatusEvento status,
        LocalDateTime dataHora,
        LocalDateTime dataAberturaVendas,
        String localFormatado,
        String imagemUrl
) {}