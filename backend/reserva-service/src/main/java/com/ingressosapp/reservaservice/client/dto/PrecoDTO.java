package com.ingressosapp.reservaservice.client.dto;

import java.math.BigDecimal;

public record PrecoDTO(String tipoIngresso, BigDecimal valor) {}