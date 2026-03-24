package com.ingressosapp.catalogoservice.domain;

import com.ingressosapp.catalogoservice.domain.enums.Categoria;
import com.ingressosapp.catalogoservice.domain.enums.StatusEvento;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "eventos")
public class Evento {

    @Id
    private String id;

    private String titulo;
    private String descricao;
    private String imagemUrl;
    private String produtora;
    private Integer classificacaoEtaria;

    @Builder.Default
    private Boolean ativo = true;

    private Categoria categoria;
    private StatusEvento status;

    private LocalDateTime dataHora;
    private LocalDateTime dataHoraFim;
    private LocalDateTime dataAberturaVendas;

    private Endereco local;

    private List<Setor> setores;

    @CreatedDate
    private LocalDateTime dataCriacao;

    @LastModifiedDate
    private LocalDateTime dataAtualizacao;

    @Version
    private Long versao;
}
