package com.ingressosapp.catalogoservice.validation;

import com.ingressosapp.catalogoservice.dto.request.EventoRequestDTO;
import com.ingressosapp.catalogoservice.exception.RegraNegocioException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ValidacaoDatasEventoTest {

    private final ValidacaoDatasEvento validacao = new ValidacaoDatasEvento();

    @Test
    @DisplayName("Deve passar sem erros quando datas forem válidas")
    void devePassarComDatasValidas() {
        EventoRequestDTO dto = mock(EventoRequestDTO.class);
        when(dto.dataHora()).thenReturn(LocalDateTime.of(2026, 10, 10, 20, 0));
        when(dto.dataHoraFim()).thenReturn(LocalDateTime.of(2026, 10, 11, 4, 0));
        when(dto.dataAberturaVendas()).thenReturn(LocalDateTime.of(2026, 9, 10, 10, 0));

        assertDoesNotThrow(() -> validacao.validar(dto));
    }

    @Test
    @DisplayName("Deve falhar se data final for anterior à inicial")
    void deveFalharDataFimAnteriorDataInicio() {
        EventoRequestDTO dto = mock(EventoRequestDTO.class);
        when(dto.dataHora()).thenReturn(LocalDateTime.of(2026, 10, 10, 20, 0));
        when(dto.dataHoraFim()).thenReturn(LocalDateTime.of(2026, 10, 9, 20, 0));

        assertThrows(RegraNegocioException.class, () -> validacao.validar(dto));
    }

    @Test
    @DisplayName("Deve falhar se data de abertura for posterior à inicial")
    void deveFalharDataAberturaPosteriorDataInicio() {
        EventoRequestDTO dto = mock(EventoRequestDTO.class);
        when(dto.dataAberturaVendas()).thenReturn(LocalDateTime.of(2026, 10, 10, 10, 0));
        when(dto.dataHora()).thenReturn(LocalDateTime.of(2026, 10, 9, 20, 0));

        assertThrows(RegraNegocioException.class, () -> validacao.validar(dto));
    }
}