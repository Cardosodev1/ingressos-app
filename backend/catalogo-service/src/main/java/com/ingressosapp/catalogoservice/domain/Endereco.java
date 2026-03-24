package com.ingressosapp.catalogoservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Endereco {
    private String nomeEspaco;
    private String logradouro;
    private String cidade;
    private String estado;
    private String cep;
}