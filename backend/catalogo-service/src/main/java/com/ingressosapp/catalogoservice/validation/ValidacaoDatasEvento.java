package com.ingressosapp.catalogoservice.validation;

import com.ingressosapp.catalogoservice.dto.request.EventoRequestDTO;
import com.ingressosapp.catalogoservice.exception.RegraNegocioException;
import org.springframework.stereotype.Component;

@Component
public class ValidacaoDatasEvento implements ValidacaoCriacaoEvento {

    @Override
    public void validar(EventoRequestDTO dto) {
        if (dto.dataHoraFim() != null && dto.dataHoraFim().isBefore(dto.dataHora())) {
            throw new RegraNegocioException("A data de fim do evento deve ser posterior à data de início.");
        }

        if (dto.dataAberturaVendas() != null && dto.dataAberturaVendas().isAfter(dto.dataHora())) {
            throw new RegraNegocioException("A data de abertura das vendas deve ser anterior à data do evento.");
        }
    }
}