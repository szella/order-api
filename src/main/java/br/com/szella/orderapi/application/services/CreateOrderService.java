package br.com.szella.orderapi.application.services;

import br.com.szella.orderapi.application.ports.in.CreateOrderCommand;
import br.com.szella.orderapi.application.ports.in.CreateOrderUseCase;
import br.com.szella.orderapi.application.ports.in.OrderResponse;
import br.com.szella.orderapi.application.ports.out.OrderEventPublisherPort;
import br.com.szella.orderapi.application.ports.out.OrderRepositoryPort;
import br.com.szella.orderapi.domain.model.Order;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreateOrderService implements CreateOrderUseCase {

    private final OrderRepositoryPort orderRepositoryPort;
    private final OrderEventPublisherPort orderEventPublisherPort;

    @Override
    public OrderResponse execute(CreateOrderCommand command) {
        Order order = new Order(
                command.customerId(),
                command.ticker(),
                command.quantity(),
                command.price(),
                command.type()
        );

        Order savedOrder = orderRepositoryPort.save(order);

        orderEventPublisherPort.publishOrderCreated(savedOrder);

        return OrderResponse.fromDomain(savedOrder);
    }
}
