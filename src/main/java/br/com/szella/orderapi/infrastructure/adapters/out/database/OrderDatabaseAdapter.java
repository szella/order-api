package br.com.szella.orderapi.infrastructure.adapters.out.database;

import br.com.szella.orderapi.application.ports.out.OrderRepositoryPort;
import br.com.szella.orderapi.domain.model.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderDatabaseAdapter implements OrderRepositoryPort {

    private final OrderJpaRepository orderJpaRepository;

    @Override
    public Order save(Order order) {
        OrderEntity orderEntity = OrderEntity.fromDomain(order);
        OrderEntity savedEntity = orderJpaRepository.save(orderEntity);

        return savedEntity.toDomain();
    }
}
