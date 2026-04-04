package com.ingressosapp.reservaservice.validation;

import com.ingressosapp.reservaservice.client.dto.EventoDetalheDTO;
import com.ingressosapp.reservaservice.dto.request.ReservaRequestDTO;

import java.util.Map;

public interface ValidacaoCriacaoReserva {
    void validar(ReservaRequestDTO dto, Map<String, EventoDetalheDTO> eventosNoCatalogo, String usuarioId);
}
