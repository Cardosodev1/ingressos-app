package com.ingressosapp.catalogoservice.domain;

import com.ingressosapp.catalogoservice.domain.enums.TipoIngresso;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Preco {
    private TipoIngresso tipoIngresso;
    private BigDecimal valor;
}
