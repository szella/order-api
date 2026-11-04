package br.com.szella.orderapi.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    @Test
    @DisplayName("Deve criar uma ordem com sucesso quando os dados forem válidos")
    void shouldCreateOrderSuccessfully() {
        Order order = new Order(UUID.randomUUID(), "PETR4", 100, new BigDecimal("35.50"), OrderType.BUY);

        assertNotNull(order.getId());
        assertEquals(OrderStatus.PENDING, order.getStatus());
        assertNotNull(order.getCreatedAt());
    }

    @Test
    @DisplayName("Deve lançar exceção quando a quantidade for zero ou negativa")
    void shouldThrowExceptionWhenQuantityIsInvalid() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                new Order(UUID.randomUUID(), "PETR4", 0, new BigDecimal("35.50"), OrderType.BUY)
        );
        assertEquals("Quantity must be greater than zero", exception.getMessage());
    }

    @Test
    @DisplayName("Deve rejeitar quantidade nula")
    void shouldThrowExceptionWhenQuantityIsNull() {
        assertThrows(IllegalArgumentException.class, () ->
                new Order(UUID.randomUUID(), "PETR4", null, new BigDecimal("35.50"), OrderType.BUY)
        );
    }

    @Test
    @DisplayName("Deve rejeitar preço nulo ou não positivo")
    void shouldThrowExceptionWhenPriceIsInvalid() {
        assertThrows(IllegalArgumentException.class, () ->
                new Order(UUID.randomUUID(), "PETR4", 100, null, OrderType.BUY)
        );
        assertThrows(IllegalArgumentException.class, () ->
                new Order(UUID.randomUUID(), "PETR4", 100, BigDecimal.ZERO, OrderType.BUY)
        );
    }

    @Test
    @DisplayName("Deve atualizar o status da ordem")
    void shouldUpdateStatus() {
        Order order = new Order(UUID.randomUUID(), "PETR4", 100, new BigDecimal("35.50"), OrderType.BUY);

        order.updateStatus(OrderStatus.PROCESSED);

        assertEquals(OrderStatus.PROCESSED, order.getStatus());
    }
}