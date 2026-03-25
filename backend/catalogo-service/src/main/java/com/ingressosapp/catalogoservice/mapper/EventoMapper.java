package com.ingressosapp.catalogoservice.mapper;

import com.ingressosapp.catalogoservice.domain.Endereco;
import com.ingressosapp.catalogoservice.domain.Evento;
import com.ingressosapp.catalogoservice.domain.Preco;
import com.ingressosapp.catalogoservice.domain.Setor;
import com.ingressosapp.catalogoservice.dto.request.EnderecoDTO;
import com.ingressosapp.catalogoservice.dto.request.EventoRequestDTO;
import com.ingressosapp.catalogoservice.dto.request.PrecoRequestDTO;
import com.ingressosapp.catalogoservice.dto.request.SetorRequestDTO;
import com.ingressosapp.catalogoservice.dto.response.EventoDetalheDTO;
import com.ingressosapp.catalogoservice.dto.response.EventoResumoDTO;
import com.ingressosapp.catalogoservice.dto.response.SetorResponseDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class EventoMapper {

    // --- 1. Mapeamento de Entrada (Request -> Entidade) ---

    public Evento toEntity(EventoRequestDTO dto) {
        if (dto == null) return null;

        return Evento.builder()
                .titulo(dto.titulo())
                .descricao(dto.descricao())
                .imagemUrl(dto.imagemUrl())
                .categoria(dto.categoria())
                .status(dto.status())
                .dataHora(dto.dataHora())
                .dataHoraFim(dto.dataHoraFim())
                .dataAberturaVendas(dto.dataAberturaVendas())
                .local(toEnderecoEntity(dto.local()))
                .produtora(dto.produtora())
                .classificacaoEtaria(dto.classificacaoEtaria())
                .setores(dto.setores() != null ?
                        dto.setores().stream()
                                .map(this::toSetorEntity)
                                .collect(Collectors.toCollection(ArrayList::new)) :
                        new ArrayList<>())
                .build();
    }

    private Endereco toEnderecoEntity(EnderecoDTO dto) {
        if (dto == null) return null;

        return Endereco.builder()
                .nomeEspaco(dto.nomeEspaco())
                .logradouro(dto.logradouro())
                .cidade(dto.cidade())
                .estado(dto.estado())
                .cep(dto.cep())
                .build();
    }

    private Setor toSetorEntity(SetorRequestDTO dto) {
        if (dto == null) return null;

        return Setor.builder()
                .id(UUID.randomUUID().toString())
                .nome(dto.nome())
                .capacidadeTotal(dto.capacidadeTotal())
                .ingressosDisponiveis(dto.capacidadeTotal())
                .precos(dto.precos() != null ?
                        dto.precos().stream()
                                .map(this::toPrecoEntity)
                                .collect(Collectors.toCollection(ArrayList::new)) :
                        new ArrayList<>())
                .build();
    }

    private Preco toPrecoEntity(PrecoRequestDTO dto) {
        if (dto == null) return null;

        return Preco.builder()
                .tipoIngresso(dto.tipoIngresso())
                .valor(dto.valor())
                .build();
    }

    // --- 2. Mapeamento de Saída (Entidade -> Response) ---

    public EventoResumoDTO toResumoDTO(Evento evento) {
        if (evento == null) return null;

        String localFormatado = evento.getLocal() != null ?
                String.format("%s - %s, %s",
                        evento.getLocal().getNomeEspaco(),
                        evento.getLocal().getCidade(),
                        evento.getLocal().getEstado()) :
                "Local não informado";

        return new EventoResumoDTO(
                evento.getId(),
                evento.getTitulo(),
                evento.getCategoria(),
                evento.getStatus(),
                evento.getDataHora(),
                evento.getDataAberturaVendas(),
                localFormatado,
                evento.getImagemUrl()
        );
    }

    public EventoDetalheDTO toDetalheDTO(Evento evento) {
        if (evento == null) return null;

        return new EventoDetalheDTO(
                evento.getId(),
                evento.getTitulo(),
                evento.getDescricao(),
                evento.getImagemUrl(),
                evento.getCategoria(),
                evento.getStatus(),
                evento.getDataHora(),
                evento.getDataHoraFim(),
                evento.getDataAberturaVendas(),
                toEnderecoDTO(evento.getLocal()),
                evento.getProdutora(),
                evento.getClassificacaoEtaria(),
                evento.getSetores() != null ?
                        evento.getSetores().stream()
                                .map(this::toSetorResponseDTO)
                                .collect(Collectors.toCollection(ArrayList::new)) :
                        new ArrayList<>()
        );
    }

    private EnderecoDTO toEnderecoDTO(Endereco endereco) {
        if (endereco == null) return null;

        return new EnderecoDTO(
                endereco.getNomeEspaco(),
                endereco.getLogradouro(),
                endereco.getCidade(),
                endereco.getEstado(),
                endereco.getCep()
        );
    }

    private SetorResponseDTO toSetorResponseDTO(Setor setor) {
        if (setor == null) return null;

        return new SetorResponseDTO(
                setor.getId(),
                setor.getNome(),
                setor.getCapacidadeTotal(),
                setor.getIngressosDisponiveis(),
                setor.getPrecos() != null ?
                        setor.getPrecos().stream()
                                .map(p -> new PrecoRequestDTO(p.getTipoIngresso(), p.getValor()))
                                .collect(Collectors.toCollection(ArrayList::new)) :
                        new ArrayList<>()
        );
    }
}