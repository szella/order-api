package br.com.szella.orderapi.application.ports.in;

import br.com.szella.orderapi.domain.model.OrderType;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateOrderCommand(
        UUID customerId,
        String ticker,
        Integer quantity,
        BigDecimal price,
        OrderType type
) {
}
