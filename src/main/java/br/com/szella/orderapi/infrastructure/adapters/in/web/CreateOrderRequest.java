package br.com.szella.orderapi.infrastructure.adapters.in.web;

import br.com.szella.orderapi.application.ports.in.CreateOrderCommand;
import br.com.szella.orderapi.domain.model.OrderType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateOrderRequest(

        @NotNull(message = "O ID do cliente é obrigatório")
        UUID customerId,

        @NotBlank(message = "O ticker do ativo é obrigatório")
        String ticker,

        @NotNull(message = "A quantidade é obrigatória")
        @Positive(message = "A quantidade deve ser maior que zero")
        Integer quantity,

        @NotNull(message = "O preço é obrigatório")
        @Positive(message = "O preço deve ser maior que zero")
        BigDecimal price,

        @NotNull(message = "O tipo de ordem (BUY/SELL) é obrigatório")
        OrderType type
) {
    public CreateOrderCommand toCommand() {
        return new CreateOrderCommand(customerId, ticker, quantity, price, type);
    }
}
