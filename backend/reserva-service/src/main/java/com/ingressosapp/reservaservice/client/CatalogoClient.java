package com.ingressosapp.reservaservice.client;

import com.ingressosapp.reservaservice.client.dto.EventoDetalheDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "catalogo-service", url = "http://localhost:8082/api/v1/eventos")
public interface CatalogoClient {

    @GetMapping("/{id}")
    EventoDetalheDTO buscarEventoPorId(@PathVariable("id") String id);

}