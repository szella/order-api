package br.com.szella.orderapi.application.ports.in;

import br.com.szella.orderapi.domain.model.Order;
import br.com.szella.orderapi.domain.model.OrderStatus;
import br.com.szella.orderapi.domain.model.OrderType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record OrderResponse(
        UUID id,
        UUID customerId,
        String ticker,
        Integer quantity,
        BigDecimal price,
        OrderType type,
        OrderStatus status,
        LocalDateTime createdAt
) {
    public static OrderResponse fromDomain(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getCustomerId(),
                order.getTicker(),
                order.getQuantity(),
                order.getPrice(),
                order.getType(),
                order.getStatus(),
                order.getCreatedAt()
        );
    }
}
