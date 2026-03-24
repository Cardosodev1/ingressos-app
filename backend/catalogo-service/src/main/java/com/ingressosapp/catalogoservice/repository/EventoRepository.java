package com.ingressosapp.catalogoservice.repository;

import com.ingressosapp.catalogoservice.domain.Evento;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventoRepository extends MongoRepository<Evento, String> {

    long countByCategoriaAndAtivoTrue(String categoria);

}
