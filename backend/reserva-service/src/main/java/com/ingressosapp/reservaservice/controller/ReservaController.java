package com.ingressosapp.reservaservice.controller;

import com.ingressosapp.reservaservice.dto.request.ReservaRequestDTO;
import com.ingressosapp.reservaservice.dto.response.ReservaResponseDTO;
import com.ingressosapp.reservaservice.service.ReservaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/reservas")
@RequiredArgsConstructor
public class ReservaController {

    private final ReservaService reservaService;

    @PostMapping
    public ResponseEntity<ReservaResponseDTO> criarReserva(
            @Valid @RequestBody ReservaRequestDTO dto,
            @AuthenticationPrincipal Jwt jwt
    ) {
        String usuarioId = jwt.getSubject();
        ReservaResponseDTO response = reservaService.criarReserva(dto, usuarioId);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();

        return ResponseEntity.created(location).body(response);
    }
}