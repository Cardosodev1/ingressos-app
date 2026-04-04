package com.ingressosapp.reservaservice.validation;

import com.ingressosapp.reservaservice.client.dto.EventoDetalheDTO;
import com.ingressosapp.reservaservice.dto.request.ReservaRequestDTO;
import com.ingressosapp.reservaservice.exception.RegraNegocioException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;

@Component
public class ValidacaoDatasEvento implements ValidacaoCriacaoReserva {

    @Override
    public void validar(ReservaRequestDTO dto, Map<String, EventoDetalheDTO> eventosNoCatalogo, String usuarioId) {
        if (dto.itens() != null) {
            dto.itens().forEach(item -> {
                EventoDetalheDTO eventoDTO = eventosNoCatalogo.get(item.eventoId());

                if (eventoDTO.dataAberturaVendas() != null && LocalDateTime.now().isBefore(eventoDTO.dataAberturaVendas())) {
                    throw new RegraNegocioException("As vendas para o evento '" + eventoDTO.titulo() + "' ainda não começaram.");
                }

                if (eventoDTO.dataHora() != null && LocalDateTime.now().isAfter(eventoDTO.dataHora())) {
                    throw new RegraNegocioException("O evento '" + eventoDTO.titulo() + "' já aconteceu ou foi encerrado.");
                }
            });
        }
    }
}