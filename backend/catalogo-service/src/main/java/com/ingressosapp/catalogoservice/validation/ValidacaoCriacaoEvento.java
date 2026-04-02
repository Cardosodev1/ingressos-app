package com.ingressosapp.catalogoservice.validation;

import com.ingressosapp.catalogoservice.dto.request.EventoRequestDTO;

public interface ValidacaoCriacaoEvento {
    void validar(EventoRequestDTO dto);
}