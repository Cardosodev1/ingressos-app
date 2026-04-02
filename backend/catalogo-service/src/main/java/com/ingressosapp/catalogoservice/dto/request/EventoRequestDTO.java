package com.ingressosapp.catalogoservice.dto.request;

import com.ingressosapp.catalogoservice.domain.enums.Categoria;
import com.ingressosapp.catalogoservice.domain.enums.StatusEvento;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

public record EventoRequestDTO(
        @NotBlank(message = "O título é obrigatório")
        String titulo,

        @NotBlank(message = "A descrição é obrigatória")
        String descricao,

        @NotBlank(message = "A URL da imagem é obrigatória")
        String imagemUrl,

        @NotNull(message = "A categoria é obrigatória")
        Categoria categoria,

        @NotNull(message = "O status inicial é obrigatório")
        StatusEvento status,

        @NotNull
        @Future(message = "A data do evento deve ser no futuro")
        LocalDateTime dataHora,

        @NotNull
        @Future(message = "O fim do evento deve ser no futuro")
        LocalDateTime dataHoraFim,

        @NotNull
        @FutureOrPresent(message = "A abertura de vendas não pode estar no passado")
        LocalDateTime dataAberturaVendas,

        @NotNull(message = "O endereço é obrigatório")
        @Valid
        EnderecoDTO local,

        @NotBlank(message = "A produtora é obrigatória")
        String produtora,

        @NotNull
        @Min(value = 0, message = "A classificação não pode ser negativa")
        Integer classificacaoEtaria,

        @NotEmpty(message = "O evento deve ter pelo menos um setor")
        @Valid
        List<SetorRequestDTO> setores
) {}