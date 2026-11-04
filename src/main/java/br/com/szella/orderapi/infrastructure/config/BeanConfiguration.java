package br.com.szella.orderapi.infrastructure.config;

import br.com.szella.orderapi.application.ports.in.CreateOrderUseCase;
import br.com.szella.orderapi.application.ports.out.OrderEventPublisherPort;
import br.com.szella.orderapi.application.ports.out.OrderRepositoryPort;
import br.com.szella.orderapi.application.services.CreateOrderService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public CreateOrderUseCase createOrderUseCase(
            OrderRepositoryPort orderRepositoryPort,
            OrderEventPublisherPort orderEventPublisherPort
    ) {
        return new CreateOrderService(orderRepositoryPort, orderEventPublisherPort);
    }
}
