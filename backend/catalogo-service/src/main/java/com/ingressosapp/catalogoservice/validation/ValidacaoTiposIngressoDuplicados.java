package com.ingressosapp.catalogoservice.validation;

import com.ingressosapp.catalogoservice.dto.request.EventoRequestDTO;
import com.ingressosapp.catalogoservice.dto.request.LoteRequestDTO;
import com.ingressosapp.catalogoservice.dto.PrecoDTO;
import com.ingressosapp.catalogoservice.dto.request.SetorRequestDTO;
import com.ingressosapp.catalogoservice.exception.RegraNegocioException;
import org.springframework.stereotype.Component;

@Component
public class ValidacaoTiposIngressoDuplicados implements ValidacaoCriacaoEvento {

    @Override
    public void validar(EventoRequestDTO dto) {
        if (dto.setores() == null) return;

        for (SetorRequestDTO setorRQ : dto.setores()) {
            if (setorRQ.lotes() == null) continue;

            for (LoteRequestDTO loteRQ : setorRQ.lotes()) {
                if (loteRQ.precos() == null) continue;

                long tiposUnicos = loteRQ.precos().stream()
                        .map(PrecoDTO::tipoIngresso)
                        .distinct()
                        .count();

                if (tiposUnicos < loteRQ.precos().size()) {
                    throw new RegraNegocioException("Existem tipos de ingresso duplicados. Cada lote deve ter apenas um preço por tipo.");
                }
            }
        }
    }
}