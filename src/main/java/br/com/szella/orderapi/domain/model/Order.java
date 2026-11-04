package br.com.szella.orderapi.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class Order {
    private final UUID id;
    private final UUID customerId;
    private final String ticker;
    private final Integer quantity;
    private final BigDecimal price;
    private final OrderType type;
    private OrderStatus status;
    private final LocalDateTime createdAt;

    public Order(UUID customerId, String ticker, Integer quantity, BigDecimal price, OrderType type) {
        validate(quantity, price);

        this.id = UUID.randomUUID();
        this.customerId = customerId;
        this.ticker = ticker;
        this.quantity = quantity;
        this.price = price;
        this.type = type;
        this.status = OrderStatus.PENDING;
        this.createdAt = LocalDateTime.now();
    }

    private void validate(Integer quantity, BigDecimal price) {
        if (quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }
        if (price == null || price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Price must be greater than zero");
        }
    }

    public void updateStatus(OrderStatus newStatus) {
        this.status = newStatus;
    }
}
