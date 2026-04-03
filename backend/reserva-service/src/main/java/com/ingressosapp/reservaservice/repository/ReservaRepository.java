package com.ingressosapp.reservaservice.repository;

import com.ingressosapp.reservaservice.domain.Reserva;
import com.ingressosapp.reservaservice.domain.enums.StatusReserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, String> {

    List<Reserva> findByUsuarioIdOrderByDataReservaDesc(String usuarioId);

    List<Reserva> findByStatusAndDataExpiracaoBefore(StatusReserva status, LocalDateTime agora);
}