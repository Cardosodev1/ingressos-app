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
public class Setor {
    private String id;
    private String nome;
    private Integer capacidadeTotal;
    private Integer ingressosDisponiveis;
    private List<Preco> precos;
}