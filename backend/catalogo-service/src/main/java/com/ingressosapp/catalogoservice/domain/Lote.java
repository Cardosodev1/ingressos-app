package com.ingressosapp.catalogoservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Lote {
    private String id;
    private String nome;
    private Integer capacidade;

    @Builder.Default
    private Integer ingressosVendidos = 0;

    private Boolean ativo;
    private List<Preco> precos;
}