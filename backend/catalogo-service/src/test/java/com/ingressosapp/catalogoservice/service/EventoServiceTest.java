package com.ingressosapp.catalogoservice.service;

import com.ingressosapp.catalogoservice.domain.Evento;
import com.ingressosapp.catalogoservice.dto.request.EventoRequestDTO;
import com.ingressosapp.catalogoservice.dto.response.EventoDetalheDTO;
import com.ingressosapp.catalogoservice.exception.RecursoNaoEncontradoException;
import com.ingressosapp.catalogoservice.exception.RegraNegocioException;
import com.ingressosapp.catalogoservice.mapper.EventoMapper;
import com.ingressosapp.catalogoservice.repository.EventoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventoServiceTest {

    @Mock
    private EventoRepository repository;

    @Mock
    private EventoMapper mapper;

    @InjectMocks
    private EventoService service;

    @Test
    @DisplayName("Deve lançar exceção quando a data de fim for ANTERIOR à data de início")
    void naoDeveCriarEventoComDataInvalida() {
        EventoRequestDTO dtoFalso = mock(EventoRequestDTO.class);

        Evento eventoInvalido = new Evento();
        eventoInvalido.setTitulo("Evento Invalido");
        eventoInvalido.setDataHora(LocalDateTime.of(2026, 12, 20, 20, 0));
        eventoInvalido.setDataHoraFim(LocalDateTime.of(2026, 12, 15, 20, 0));

        when(mapper.toEntity(any())).thenReturn(eventoInvalido);

        RegraNegocioException exception = assertThrows(RegraNegocioException.class, () ->
                service.criar(dtoFalso));

        assertEquals("A data de fim do evento não pode ser anterior à data de início.", exception.getMessage());

        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Deve salvar evento com sucesso quando as datas forem válidas")
    void deveCriarEventoComSucesso() {
        EventoRequestDTO dtoFalso = mock(EventoRequestDTO.class);

        Evento eventoValido = new Evento();
        eventoValido.setTitulo("Evento Correto");
        eventoValido.setDataHora(LocalDateTime.of(2026, 12, 15, 20, 0));
        eventoValido.setDataHoraFim(LocalDateTime.of(2026, 12, 20, 20, 0)); // Tudo certo aqui!

        EventoDetalheDTO respostaEsperada = mock(EventoDetalheDTO.class);

        when(mapper.toEntity(any())).thenReturn(eventoValido);
        when(repository.save(any(Evento.class))).thenReturn(eventoValido);
        when(mapper.toDetalheDTO(any(Evento.class))).thenReturn(respostaEsperada);

        EventoDetalheDTO resultado = service.criar(dtoFalso);

        assertNotNull(resultado);
        assertTrue(eventoValido.getAtivo());
        assertNotNull(eventoValido.getDataCriacao());

        verify(repository, times(1)).save(eventoValido);
    }

    @Test
    @DisplayName("Deve buscar e retornar os detalhes do evento com sucesso")
    void deveBuscarEventoPorIdComSucesso() {
        String idValido = "69c343df14792c83525a8966";
        Evento eventoMock = new Evento();
        eventoMock.setId(idValido);
        EventoDetalheDTO dtoEsperado = mock(EventoDetalheDTO.class);

        when(repository.findByIdAndAtivoTrue(idValido)).thenReturn(java.util.Optional.of(eventoMock));
        when(mapper.toDetalheDTO(eventoMock)).thenReturn(dtoEsperado);

        EventoDetalheDTO resultado = service.buscarPorId(idValido);

        assertNotNull(resultado);
        verify(repository, times(1)).findByIdAndAtivoTrue(idValido);
    }

    @Test
    @DisplayName("Deve lançar RecursoNaoEncontradoException ao buscar evento inexistente ou inativo")
    void deveLancarExcecaoAoBuscarEventoInexistente() {
        String idInvalido = "id-fantasma";

        when(repository.findByIdAndAtivoTrue(idInvalido)).thenReturn(java.util.Optional.empty());

        RecursoNaoEncontradoException exception = assertThrows(RecursoNaoEncontradoException.class, () ->
                service.buscarPorId(idInvalido));

        assertEquals("Evento não encontrado com o ID: " + idInvalido, exception.getMessage());
        verify(mapper, never()).toDetalheDTO(any());
    }

    @Test
    @DisplayName("Deve alterar o status para inativo e salvar no banco ao excluir")
    void deveInativarEventoComSucesso() {
        String idValido = "12345";
        Evento eventoAtivo = new Evento();
        eventoAtivo.setId(idValido);
        eventoAtivo.setAtivo(true);

        when(repository.findByIdAndAtivoTrue(idValido)).thenReturn(java.util.Optional.of(eventoAtivo));

        service.inativar(idValido);

        assertFalse(eventoAtivo.getAtivo());
        verify(repository, times(1)).save(eventoAtivo);
    }
}