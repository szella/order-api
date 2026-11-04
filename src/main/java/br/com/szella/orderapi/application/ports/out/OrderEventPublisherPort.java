package br.com.szella.orderapi.application.ports.out;

import br.com.szella.orderapi.domain.model.Order;

public interface OrderEventPublisherPort {
    void publishOrderCreated(Order order);
}
