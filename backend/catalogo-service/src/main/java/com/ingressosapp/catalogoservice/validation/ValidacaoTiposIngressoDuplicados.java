package com.ingressosapp.catalogoservice.validation;

import com.ingressosapp.catalogoservice.dto.request.EventoRequestDTO;
import com.ingressosapp.catalogoservice.dto.request.LoteRequestDTO;
import com.ingressosapp.catalogoservice.dto.request.PrecoRequestDTO;
import com.ingressosapp.catalogoservice.dto.request.SetorRequestDTO;
import com.ingressosapp.catalogoservice.exception.RegraNegocioException;
import org.springframework.stereotype.Component;

@Component
public class ValidacaoTiposIngressoDuplicados implements ValidacaoCriacaoEvento {

    @Override
    public void validar(EventoRequestDTO dto) {
        if (dto.setores() == null) return;

        for (SetorRequestDTO setor : dto.setores()) {
            if (setor.lotes() == null) continue;

            for (LoteRequestDTO lote : setor.lotes()) {
                if (lote.precos() == null) continue;

                long tiposUnicos = lote.precos().stream()
                        .map(PrecoRequestDTO::tipoIngresso)
                        .distinct()
                        .count();

                if (tiposUnicos < lote.precos().size()) {
                    throw new RegraNegocioException("Existem tipos de ingresso duplicados. Cada lote deve ter apenas um preço por tipo.");
                }
            }
        }
    }
}