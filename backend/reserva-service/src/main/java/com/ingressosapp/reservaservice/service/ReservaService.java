package com.ingressosapp.reservaservice.service;

import com.ingressosapp.reservaservice.client.CatalogoClient;
import com.ingressosapp.reservaservice.client.dto.EventoDetalheDTO;
import com.ingressosapp.reservaservice.client.dto.PrecoDTO;
import com.ingressosapp.reservaservice.domain.ItemReserva;
import com.ingressosapp.reservaservice.domain.Reserva;
import com.ingressosapp.reservaservice.dto.request.ItemReservaRequestDTO;
import com.ingressosapp.reservaservice.dto.request.ReservaRequestDTO;
import com.ingressosapp.reservaservice.dto.response.ReservaResponseDTO;
import com.ingressosapp.reservaservice.exception.RecursoNaoEncontradoException;
import com.ingressosapp.reservaservice.exception.RegraNegocioException;
import com.ingressosapp.reservaservice.mapper.ReservaMapper;
import com.ingressosapp.reservaservice.repository.ReservaRepository;
import com.ingressosapp.reservaservice.validation.ValidacaoCriacaoReserva;
import feign.FeignException;
import feign.FeignException.NotFound;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final CatalogoClient catalogoClient;
    private final ReservaMapper reservaMapper;
    private final List<ValidacaoCriacaoReserva> validacoes;

    @Transactional
    public ReservaResponseDTO criarReserva(ReservaRequestDTO dto, String usuarioId) {
        Map<String, EventoDetalheDTO> eventosCache = buscarEventosParaValidacao(dto);
        validacoes.forEach(v -> v.validar(dto, eventosCache, usuarioId));

        LocalDateTime dataExpiracaoCalculada = calcularDataExpiracao(dto, eventosCache);
        Reserva reserva = reservaMapper.toEntity(usuarioId, dataExpiracaoCalculada);

        for (ItemReservaRequestDTO itemReservaRQ : dto.itens()) {
            EventoDetalheDTO eventoDTO = eventosCache.get(itemReservaRQ.eventoId());

            BigDecimal precoUnitario = extrairPreco(eventoDTO, itemReservaRQ);

            ItemReserva itemReserva = reservaMapper.toItemEntity(itemReservaRQ);
            itemReserva.setPrecoUnitario(precoUnitario);

            reserva.adicionarItem(itemReserva);
        }

        reserva.calcularTotal();
        log.info("Criando nova reserva com id: {} e valor: {}", reserva.getId(), reserva.getValorTotal());
        return reservaMapper.toResponseDTO(reservaRepository.save(reserva));
    }

    private Map<String, EventoDetalheDTO> buscarEventosParaValidacao(ReservaRequestDTO dto) {
        Map<String, EventoDetalheDTO> eventosCache = new HashMap<>();
        if (dto.itens() != null) {
            dto.itens().forEach(item -> eventosCache.computeIfAbsent(item.eventoId(), this::buscarEventoNoCatalogo));
        }
        return eventosCache;
    }

    private EventoDetalheDTO buscarEventoNoCatalogo(String eventoId) {
        try {
            log.info("Buscando dados do evento {} no catalogo-service", eventoId);
            return catalogoClient.buscarEventoPorId(eventoId);
        } catch (NotFound ex) {
            throw new RecursoNaoEncontradoException("Evento não encontrado no catálogo: " + eventoId);
        } catch (FeignException ex) {
            throw new RegraNegocioException("O serviço de catálogo está temporariamente indisponível.");
        }
    }

    private LocalDateTime calcularDataExpiracao(ReservaRequestDTO dto, Map<String, EventoDetalheDTO> eventosCache) {
        LocalDateTime tempoPadrao = LocalDateTime.now().plusMinutes(10);

        if (dto.itens() == null || dto.itens().isEmpty()) {
            return tempoPadrao;
        }

        LocalDateTime dataEventoMaisProximo = dto.itens().stream()
                .map(item -> eventosCache.get(item.eventoId()))
                .filter(evento -> evento != null && evento.dataHora() != null)
                .map(EventoDetalheDTO::dataHora)
                .min(LocalDateTime::compareTo)
                .orElse(tempoPadrao);

        return dataEventoMaisProximo.isBefore(tempoPadrao) ? dataEventoMaisProximo : tempoPadrao;
    }

    private BigDecimal extrairPreco(EventoDetalheDTO eventoDTO, ItemReservaRequestDTO itemReservaRQ) {
        return eventoDTO.setores().stream()
                .filter(s -> s.id().equals(itemReservaRQ.setorId()))
                .flatMap(s -> s.lotes().stream())
                .filter(l -> l.id().equals(itemReservaRQ.loteId()))
                .flatMap(l -> l.precos().stream())
                .filter(p -> p.tipoIngresso().equalsIgnoreCase(itemReservaRQ.tipoIngresso()))
                .findFirst()
                .map(PrecoDTO::valor)
                .orElseThrow(() -> new RegraNegocioException(
                        String.format("Preço não encontrado para o tipo de ingresso '%s' no lote '%s'",
                                itemReservaRQ.tipoIngresso(), itemReservaRQ.loteId())
                ));
    }
}