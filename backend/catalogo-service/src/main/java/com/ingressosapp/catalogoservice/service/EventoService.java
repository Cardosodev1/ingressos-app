package com.ingressosapp.catalogoservice.service;

import com.ingressosapp.catalogoservice.domain.Evento;
import com.ingressosapp.catalogoservice.domain.enums.Categoria;
import com.ingressosapp.catalogoservice.dto.request.EventoRequestDTO;
import com.ingressosapp.catalogoservice.dto.response.EventoDetalheDTO;
import com.ingressosapp.catalogoservice.dto.response.EventoResumoDTO;
import com.ingressosapp.catalogoservice.exception.RecursoNaoEncontradoException;
import com.ingressosapp.catalogoservice.exception.RegraNegocioException;
import com.ingressosapp.catalogoservice.mapper.EventoMapper;
import com.ingressosapp.catalogoservice.repository.EventoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventoService {

    private final EventoRepository repository;
    private final EventoMapper mapper;

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
        Evento evento = buscarEntidadePorId(id);
        return mapper.toDetalheDTO(evento);
    }

    @CacheEvict(value = "eventos", allEntries = true)
    public EventoDetalheDTO criar(EventoRequestDTO dto) {
        Evento evento = mapper.toEntity(dto);

        if (evento.getDataHoraFim().isBefore(evento.getDataHora())) {
            throw new RegraNegocioException("A data de fim do evento não pode ser anterior à data de início.");
        }

        evento.setAtivo(true);
        evento.setDataCriacao(LocalDateTime.now());
        log.info("Criando novo evento com título: {}", evento.getTitulo());

        Evento eventoSalvo = repository.save(evento);
        return mapper.toDetalheDTO(eventoSalvo);
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