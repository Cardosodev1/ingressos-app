package com.ingressosapp.reservaservice.validation;

import com.ingressosapp.reservaservice.client.dto.EventoDetalheDTO;
import com.ingressosapp.reservaservice.dto.request.ItemReservaRequestDTO;
import com.ingressosapp.reservaservice.dto.request.ReservaRequestDTO;
import com.ingressosapp.reservaservice.exception.RegraNegocioException;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ValidacaoLimiteIngressosPorUsuario implements ValidacaoCriacaoReserva {

    private static final int LIMITE_MAXIMO_POR_EVENTO = 6;

    @Override
    public void validar(ReservaRequestDTO dto, Map<String, EventoDetalheDTO> eventosNoCatalogo, String usuarioId) {
        if (dto.itens() == null) return;

        Map<String, Integer> ingressosPorEvento = dto.itens().stream()
                .collect(Collectors.toMap(
                        ItemReservaRequestDTO::eventoId,
                        ItemReservaRequestDTO::quantidade,
                        Integer::sum
                ));

        ingressosPorEvento.forEach((eventoId, totalIngressos) -> {
            if (totalIngressos > LIMITE_MAXIMO_POR_EVENTO) {
                EventoDetalheDTO evento = eventosNoCatalogo.get(eventoId);
                throw new RegraNegocioException(String.format(
                        "Você só pode comprar no máximo %d ingressos para o evento '%s'.",
                        LIMITE_MAXIMO_POR_EVENTO, evento.titulo()
                ));
            }
        });
    }
}