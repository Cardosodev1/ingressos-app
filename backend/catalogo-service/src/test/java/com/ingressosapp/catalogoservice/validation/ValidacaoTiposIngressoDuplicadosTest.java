package com.ingressosapp.catalogoservice.validation;

import com.ingressosapp.catalogoservice.domain.enums.TipoIngresso;
import com.ingressosapp.catalogoservice.dto.request.EventoRequestDTO;
import com.ingressosapp.catalogoservice.dto.request.LoteRequestDTO;
import com.ingressosapp.catalogoservice.dto.request.PrecoRequestDTO;
import com.ingressosapp.catalogoservice.dto.request.SetorRequestDTO;
import com.ingressosapp.catalogoservice.exception.RegraNegocioException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ValidacaoTiposIngressoDuplicadosTest {

    private final ValidacaoTiposIngressoDuplicados validacao = new ValidacaoTiposIngressoDuplicados();

    @Test
    @DisplayName("Deve passar quando não houver tipos de ingressos duplicados no lote")
    void devePassarSemDuplicatas() {
        PrecoRequestDTO precoMeia = mock(PrecoRequestDTO.class);
        when(precoMeia.tipoIngresso()).thenReturn(TipoIngresso.MEIA_ESTUDANTE);

        PrecoRequestDTO precoInteira = mock(PrecoRequestDTO.class);
        when(precoInteira.tipoIngresso()).thenReturn(TipoIngresso.INTEIRA);

        LoteRequestDTO lote = mock(LoteRequestDTO.class);
        when(lote.precos()).thenReturn(List.of(precoMeia, precoInteira));

        SetorRequestDTO setor = mock(SetorRequestDTO.class);
        when(setor.lotes()).thenReturn(List.of(lote));

        EventoRequestDTO dto = mock(EventoRequestDTO.class);
        when(dto.setores()).thenReturn(List.of(setor));

        assertDoesNotThrow(() -> validacao.validar(dto));
    }

    @Test
    @DisplayName("Deve falhar quando houver tipos de ingressos duplicados no lote")
    void deveFalharComDuplicatas() {
        PrecoRequestDTO precoInteira1 = mock(PrecoRequestDTO.class);
        when(precoInteira1.tipoIngresso()).thenReturn(TipoIngresso.INTEIRA);

        PrecoRequestDTO precoInteira2 = mock(PrecoRequestDTO.class);
        when(precoInteira2.tipoIngresso()).thenReturn(TipoIngresso.INTEIRA);

        LoteRequestDTO lote = mock(LoteRequestDTO.class);
        when(lote.nome()).thenReturn("Lote 1");
        when(lote.precos()).thenReturn(List.of(precoInteira1, precoInteira2));

        SetorRequestDTO setor = mock(SetorRequestDTO.class);
        when(setor.nome()).thenReturn("Pista VIP");
        when(setor.lotes()).thenReturn(List.of(lote));

        EventoRequestDTO dto = mock(EventoRequestDTO.class);
        when(dto.setores()).thenReturn(List.of(setor));

        assertThrows(RegraNegocioException.class, () -> validacao.validar(dto));
    }
}