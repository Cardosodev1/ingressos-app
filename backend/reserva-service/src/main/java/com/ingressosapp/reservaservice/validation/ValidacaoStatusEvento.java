package com.ingressosapp.reservaservice.validation;

import com.ingressosapp.reservaservice.client.dto.EventoDetalheDTO;
import com.ingressosapp.reservaservice.dto.request.ReservaRequestDTO;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ValidacaoStatusEvento implements ValidacaoCriacaoReserva {

    @Override
    public void validar(ReservaRequestDTO dto, Map<String, EventoDetalheDTO> eventosNoCatalogo, String usuarioId) {
        if (dto.itens() != null) {
            dto.itens().forEach(item -> {
                EventoDetalheDTO eventoDTO = eventosNoCatalogo.get(item.eventoId());

                if (eventoDTO.ativo() == null || !eventoDTO.ativo()) {
                    throw new RuntimeException("O evento '" + eventoDTO.titulo() + "' está inativo.");
                }

                if (!"VENDAS_ABERTAS".equals(eventoDTO.status())) {
                    throw new RuntimeException("O evento '" + eventoDTO.titulo() + "' não está disponível para vendas.");
                }
            });
        }
    }
}