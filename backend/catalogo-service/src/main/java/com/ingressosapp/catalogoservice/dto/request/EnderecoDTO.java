package com.ingressosapp.catalogoservice.dto.request;

import jakarta.validation.constraints.NotBlank;

public record EnderecoDTO(
        @NotBlank String nomeEspaco,
        @NotBlank String logradouro,
        @NotBlank String cidade,
        @NotBlank String estado,
        @NotBlank String cep
) {}