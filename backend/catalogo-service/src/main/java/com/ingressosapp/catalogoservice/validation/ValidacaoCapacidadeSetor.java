package com.ingressosapp.catalogoservice.validation;

import com.ingressosapp.catalogoservice.dto.request.EventoRequestDTO;
import com.ingressosapp.catalogoservice.dto.request.LoteRequestDTO;
import com.ingressosapp.catalogoservice.dto.request.SetorRequestDTO;
import com.ingressosapp.catalogoservice.exception.RegraNegocioException;
import org.springframework.stereotype.Component;

@Component
public class ValidacaoCapacidadeSetor implements ValidacaoCriacaoEvento {

    @Override
    public void validar(EventoRequestDTO dto) {
        if (dto.setores() == null) return;

        for (SetorRequestDTO setor : dto.setores()) {
            if (setor.lotes() != null) {
                int somaLotes = setor.lotes().stream()
                        .mapToInt(LoteRequestDTO::capacidade)
                        .sum();

                if (somaLotes > setor.capacidadeTotal()) {
                    throw new RegraNegocioException(String.format(
                            "A soma da capacidade dos lotes (%d) não pode exceder a capacidade total do setor (%d).",
                            somaLotes, setor.capacidadeTotal()));
                }
            }
        }
    }
}