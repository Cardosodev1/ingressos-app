package com.ingressosapp.catalogoservice.validation;

import com.ingressosapp.catalogoservice.dto.request.EventoRequestDTO;
import com.ingressosapp.catalogoservice.dto.request.LoteRequestDTO;
import com.ingressosapp.catalogoservice.dto.request.SetorRequestDTO;
import com.ingressosapp.catalogoservice.exception.RegraNegocioException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ValidacaoCapacidadeSetorTest {

    private final ValidacaoCapacidadeSetor validacao = new ValidacaoCapacidadeSetor();

    @Test
    @DisplayName("Deve passar quando a soma dos lotes for menor ou igual à capacidade do setor")
    void devePassarQuandoCapacidadeValida() {
        LoteRequestDTO lote = mock(LoteRequestDTO.class);
        when(lote.capacidade()).thenReturn(400);

        SetorRequestDTO setor = mock(SetorRequestDTO.class);
        when(setor.capacidadeTotal()).thenReturn(500);
        when(setor.lotes()).thenReturn(List.of(lote));

        EventoRequestDTO dto = mock(EventoRequestDTO.class);
        when(dto.setores()).thenReturn(List.of(setor));

        assertDoesNotThrow(() -> validacao.validar(dto));
    }

    @Test
    @DisplayName("Deve falhar quando a soma dos lotes exceder o setor")
    void deveFalharQuandoOverbooking() {
        LoteRequestDTO lote = mock(LoteRequestDTO.class);
        when(lote.capacidade()).thenReturn(600);

        SetorRequestDTO setor = mock(SetorRequestDTO.class);
        when(setor.nome()).thenReturn("Pista");
        when(setor.capacidadeTotal()).thenReturn(500);
        when(setor.lotes()).thenReturn(List.of(lote));

        EventoRequestDTO dto = mock(EventoRequestDTO.class);
        when(dto.setores()).thenReturn(List.of(setor));

        assertThrows(RegraNegocioException.class, () -> validacao.validar(dto));
    }
}