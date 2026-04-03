package com.ingressosapp.catalogoservice.service;

import com.ingressosapp.catalogoservice.domain.Evento;
import com.ingressosapp.catalogoservice.dto.request.EventoRequestDTO;
import com.ingressosapp.catalogoservice.dto.response.EventoDetalheDTO;
import com.ingressosapp.catalogoservice.exception.RecursoNaoEncontradoException;
import com.ingressosapp.catalogoservice.exception.RegraNegocioException;
import com.ingressosapp.catalogoservice.mapper.EventoMapper;
import com.ingressosapp.catalogoservice.repository.EventoRepository;
import com.ingressosapp.catalogoservice.validation.ValidacaoCriacaoEvento;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventoServiceTest {

    @Mock
    private EventoRepository repository;

    @Mock
    private EventoMapper mapper;

    @Mock
    private ValidacaoCriacaoEvento validacaoMock;

    @InjectMocks
    private EventoService service;

    @BeforeEach
    void setUp() {
        service = new EventoService(repository, mapper, List.of(validacaoMock));
    }

    @Test
    @DisplayName("Deve executar as validações e salvar o evento com sucesso")
    void deveCriarEventoComSucesso() {
        EventoRequestDTO dto = mock(EventoRequestDTO.class);
        Evento eventoValido = new Evento();
        eventoValido.setTitulo("Evento Correto");

        EventoDetalheDTO respostaEsperada = mock(EventoDetalheDTO.class);

        when(mapper.toEntity(any())).thenReturn(eventoValido);
        when(repository.save(any(Evento.class))).thenReturn(eventoValido);
        when(mapper.toDetalheDTO(any(Evento.class))).thenReturn(respostaEsperada);

        EventoDetalheDTO resultado = service.criar(dto);

        assertNotNull(resultado);
        verify(validacaoMock, times(1)).validar(dto);
        verify(repository, times(1)).save(eventoValido);
    }

    @Test
    @DisplayName("NÃO deve salvar o evento se uma validação lançar exceção")
    void naoDeveCriarEventoSeValidacaoFalhar() {
        EventoRequestDTO dto = mock(EventoRequestDTO.class);

        doThrow(new RegraNegocioException("Erro de Validação")).when(validacaoMock).validar(dto);

        assertThrows(RegraNegocioException.class, () -> service.criar(dto));

        verify(mapper, never()).toEntity(any());
        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Deve buscar e retornar os detalhes do evento com sucesso")
    void deveBuscarEventoPorIdComSucesso() {
        String idValido = "123";
        Evento eventoMock = new Evento();
        eventoMock.setId(idValido);
        EventoDetalheDTO dtoEsperado = mock(EventoDetalheDTO.class);

        when(repository.findByIdAndAtivoTrue(idValido)).thenReturn(Optional.of(eventoMock));
        when(mapper.toDetalheDTO(eventoMock)).thenReturn(dtoEsperado);

        assertNotNull(service.buscarPorId(idValido));
    }

    @Test
    @DisplayName("Deve lançar RecursoNaoEncontradoException ao buscar evento inexistente")
    void deveLancarExcecaoAoBuscarEventoInexistente() {
        String idInvalido = "123";

        when(repository.findByIdAndAtivoTrue(idInvalido)).thenReturn(Optional.empty());
        assertThrows(RecursoNaoEncontradoException.class, () -> service.buscarPorId(idInvalido));
    }

    @Test
    @DisplayName("Deve inativar evento com sucesso")
    void deveInativarEventoComSucesso() {
        String idValido = "123";
        Evento evento = new Evento();
        evento.setAtivo(true);

        when(repository.findByIdAndAtivoTrue(idValido)).thenReturn(Optional.of(evento));

        service.inativar(idValido);

        assertFalse(evento.getAtivo());
        verify(repository, times(1)).save(evento);
    }
}