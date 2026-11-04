package br.com.szella.orderapi.application.ports.in;

public interface CreateOrderUseCase {
    OrderResponse execute(CreateOrderCommand command);
}
