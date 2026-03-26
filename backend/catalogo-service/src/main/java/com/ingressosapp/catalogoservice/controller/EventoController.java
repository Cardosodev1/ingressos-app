package com.ingressosapp.catalogoservice.controller;

import com.ingressosapp.catalogoservice.domain.enums.Categoria;
import com.ingressosapp.catalogoservice.dto.request.EventoRequestDTO;
import com.ingressosapp.catalogoservice.dto.response.EventoDetalheDTO;
import com.ingressosapp.catalogoservice.dto.response.EventoResumoDTO;
import com.ingressosapp.catalogoservice.service.EventoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/eventos")
@RequiredArgsConstructor
public class EventoController {

    private final EventoService eventoService;

    @GetMapping
    public ResponseEntity<Page<EventoResumoDTO>> listarEventos(
            @RequestParam(required = false) Categoria categoria,
            @PageableDefault(size = 20, sort = "dataHora") Pageable pageable) {

        return ResponseEntity.ok(eventoService.listarEventos(categoria, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventoDetalheDTO> buscarPorId(@PathVariable String id) {
        return ResponseEntity.ok(eventoService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<EventoDetalheDTO> criarEvento(@Valid @RequestBody EventoRequestDTO dto) {
        EventoDetalheDTO response = eventoService.criar(dto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();

        return ResponseEntity.created(location).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> inativarEvento(@PathVariable String id) {
        eventoService.inativar(id);
        return ResponseEntity.noContent().build();
    }
}