package com.ingressosapp.reservaservice.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public record ReservaRequestDTO(
        @NotEmpty(message = "O carrinho não pode estar vazio")
        @Valid
        List<ItemReservaRequestDTO> itens
) {}