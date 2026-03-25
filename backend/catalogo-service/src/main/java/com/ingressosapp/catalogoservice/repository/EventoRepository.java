package com.ingressosapp.catalogoservice.repository;

import com.ingressosapp.catalogoservice.domain.Evento;
import com.ingressosapp.catalogoservice.domain.enums.Categoria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EventoRepository extends MongoRepository<Evento, String> {

    long countByCategoriaAndAtivoTrue(String categoria);

    Optional<Evento> findByIdAndAtivoTrue(String id);

    Page<Evento> findByAtivoTrue(Pageable pageable);

    Page<Evento> findByCategoriaAndAtivoTrue(Categoria categoria, Pageable pageable);
}
