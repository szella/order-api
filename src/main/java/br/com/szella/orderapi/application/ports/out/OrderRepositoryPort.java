package br.com.szella.orderapi.application.ports.out;

import br.com.szella.orderapi.domain.model.Order;

public interface OrderRepositoryPort {
    Order save(Order order);
}
