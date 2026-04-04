package com.ingressosapp.reservaservice.validation;

import com.ingressosapp.reservaservice.client.dto.EventoDetalheDTO;
import com.ingressosapp.reservaservice.client.dto.LoteDTO;
import com.ingressosapp.reservaservice.dto.request.ReservaRequestDTO;
import org.springframework.stereotype.Component;
import java.util.Map;

@Component
public class ValidacaoEstoqueLote implements ValidacaoCriacaoReserva {

    @Override
    public void validar(ReservaRequestDTO dto, Map<String, EventoDetalheDTO> eventosNoCatalogo, String usuarioId) {
        if (dto.itens() != null) {
            dto.itens().forEach(item -> {
                EventoDetalheDTO eventoDTO = eventosNoCatalogo.get(item.eventoId());

                LoteDTO lote = eventoDTO.setores().stream()
                        .filter(s -> s.id().equals(item.setorId()))
                        .flatMap(s -> s.lotes().stream())
                        .filter(l -> l.id().equals(item.loteId()))
                        .findFirst()
                        .orElseThrow(() -> new RuntimeException("Lote não encontrado no catálogo."));

                if (!lote.ativo()) {
                    throw new RuntimeException("O lote '" + lote.nome() + "' não está mais ativo.");
                }

                if (lote.getQuantidadeDisponivel() < item.quantidade()) {
                    throw new RuntimeException(String.format("Estoque insuficiente para o lote '%s'. Disponível: %d",
                            lote.nome(), lote.getQuantidadeDisponivel()));
                }

            });
        }
    }
}