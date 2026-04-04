package com.ingressosapp.catalogoservice.service;

import com.ingressosapp.catalogoservice.domain.Evento;
import com.ingressosapp.catalogoservice.domain.enums.Categoria;
import com.ingressosapp.catalogoservice.dto.request.EventoRequestDTO;
import com.ingressosapp.catalogoservice.dto.response.EventoDetalheDTO;
import com.ingressosapp.catalogoservice.dto.response.EventoResumoDTO;
import com.ingressosapp.catalogoservice.exception.RecursoNaoEncontradoException;
import com.ingressosapp.catalogoservice.mapper.EventoMapper;
import com.ingressosapp.catalogoservice.repository.EventoRepository;
import com.ingressosapp.catalogoservice.validation.ValidacaoCriacaoEvento;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventoService {

    private final EventoRepository repository;
    private final EventoMapper mapper;
    private final List<ValidacaoCriacaoEvento> validacoes;

    public Page<EventoResumoDTO> listarEventos(Categoria categoria, Pageable pageable) {
        Page<Evento> eventosPage;
        if (categoria != null) {
            eventosPage = repository.findByCategoriaAndAtivoTrue(categoria, pageable);
        } else {
            eventosPage = repository.findByAtivoTrue(pageable);
        }
        return eventosPage.map(mapper::toResumoDTO);
    }

    @Cacheable(value = "eventos", key = "#id")
    public EventoDetalheDTO buscarPorId(String id) {
        return mapper.toDetalheDTO(buscarEntidadePorId(id));
    }

    @CacheEvict(value = "eventos", allEntries = true)
    public EventoDetalheDTO criar(EventoRequestDTO dto) {
        validacoes.forEach(v -> v.validar(dto));
        Evento evento = mapper.toEntity(dto);
        evento.criar();
        log.info("Criando novo evento com título: {}", evento.getTitulo());
        return mapper.toDetalheDTO(repository.save(evento));
    }

    @CacheEvict(value = "eventos", key = "#id")
    public void inativar(String id) {
        Evento evento = buscarEntidadePorId(id);
        evento.setAtivo(false);
        repository.save(evento);
        log.info("Evento {} inativado com sucesso.", id);
    }

    private Evento buscarEntidadePorId(String id) {
        log.info("Buscando evento {} no MongoDB (Cache Miss)", id);
        return repository.findByIdAndAtivoTrue(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Evento não encontrado com o ID: " + id));
    }
}