package com.ingressosapp.reservaservice.validation;

import com.ingressosapp.reservaservice.client.dto.EventoDetalheDTO;
import com.ingressosapp.reservaservice.domain.enums.StatusReserva;
import com.ingressosapp.reservaservice.dto.request.ReservaRequestDTO;
import com.ingressosapp.reservaservice.repository.ReservaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class ValidacaoReservaPendenteDuplicada implements ValidacaoCriacaoReserva {

    private final ReservaRepository reservaRepository;

    @Override
    public void validar(ReservaRequestDTO dto, Map<String, EventoDetalheDTO> eventosNoCatalogo, String usuarioId) {

        boolean possuiReservaPendente = reservaRepository.existsByUsuarioIdAndStatus(usuarioId, StatusReserva.PENDENTE);

        if (possuiReservaPendente) {
            throw new RuntimeException("Você já possui uma reserva em andamento. Conclua o pagamento ou aguarde a expiração antes de iniciar uma nova compra.");
        }
    }
}