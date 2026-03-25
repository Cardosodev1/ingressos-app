package com.ingressosapp.catalogoservice.service;

import com.ingressosapp.catalogoservice.domain.Evento;
import com.ingressosapp.catalogoservice.domain.enums.Categoria;
import com.ingressosapp.catalogoservice.exception.RecursoNaoEncontradoException;
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

    @Cacheable(value = "eventos", key = "#id")
    public Evento buscarPorId(String id) {
        log.info("Buscando evento {} no MongoDB (Cache Miss)", id);
        return repository.findByIdAndAtivoTrue(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Evento não encontrado com o ID: " + id));
    }

    public Page<Evento> listarEventos(Categoria categoria, Pageable pageable) {
        if (categoria != null) {
            return repository.findByCategoriaAndAtivoTrue(categoria, pageable);
        }
        return repository.findByAtivoTrue(pageable);
    }

    @CacheEvict(value = "eventos", allEntries = true)
    public Evento criar(Evento evento) {
        evento.setAtivo(true);
        evento.setDataCriacao(LocalDateTime.now());
        return repository.save(evento);
    }

    @CacheEvict(value = "eventos", key = "#id")
    public void inativar(String id) {
        Evento evento = buscarPorId(id);
        evento.setAtivo(false);
        repository.save(evento);
    }
}