package br.com.szella.orderapi.infrastructure.adapters.out.database;

import br.com.szella.orderapi.domain.model.Order;
import br.com.szella.orderapi.domain.model.OrderStatus;
import br.com.szella.orderapi.domain.model.OrderType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderEntity {

    @Id
    private UUID id;

    @Column(nullable = false)
    private UUID customerId;

    @Column(nullable = false)
    private String ticker;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderType type;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public static OrderEntity fromDomain(Order order) {
        return new OrderEntity(
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

    public Order toDomain() {
        return new Order(
                this.id,
                this.customerId,
                this.ticker,
                this.quantity,
                this.price,
                this.type,
                this.status,
                this.createdAt
        );
    }
}
