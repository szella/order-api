package br.com.szella.orderapi.application.services;

import br.com.szella.orderapi.application.ports.in.CreateOrderCommand;
import br.com.szella.orderapi.application.ports.in.OrderResponse;
import br.com.szella.orderapi.application.ports.out.OrderEventPublisherPort;
import br.com.szella.orderapi.application.ports.out.OrderRepositoryPort;
import br.com.szella.orderapi.domain.model.Order;
import br.com.szella.orderapi.domain.model.OrderType;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateOrderServiceTest {

    @Mock
    private OrderRepositoryPort orderRepositoryPort;

    @Mock
    private OrderEventPublisherPort orderEventPublisherPort;

    @InjectMocks
    private CreateOrderService createOrderService;

    @Test
    @DisplayName("Deve salvar no banco e publicar evento no Kafka ao criar ordem")
    void shouldSaveAndPublishEventWhenCreatingOrder() {
        // Arrange
        CreateOrderCommand command = new CreateOrderCommand(
                UUID.randomUUID(), "VALE3", 50, new BigDecimal("60.00"), OrderType.SELL);

        when(orderRepositoryPort.save(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        OrderResponse response = createOrderService.execute(command);

        // Assert
        assertNotNull(response.id());
        verify(orderRepositoryPort, times(1)).save(any(Order.class));
        verify(orderEventPublisherPort, times(1)).publishOrderCreated(any(Order.class));
    }
}
