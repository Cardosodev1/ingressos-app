package com.ingressosapp.catalogoservice.dto;

import jakarta.validation.constraints.NotBlank;

public record EnderecoDTO(
        @NotBlank(message = "A nome do espaço é obrigatório")
        String nomeEspaco,

        @NotBlank(message = "O logradouro é obrigatório")
        String logradouro,

        @NotBlank(message = "A cidade é obrigatória")
        String cidade,

        @NotBlank(message = "O estado é obrigatório")
        String estado,

        @NotBlank(message = "O cep é obrigatório")
        String cep
) {}