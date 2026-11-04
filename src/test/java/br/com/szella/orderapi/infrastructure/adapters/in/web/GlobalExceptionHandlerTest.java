package br.com.szella.orderapi.infrastructure.adapters.in.web;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.core.MethodParameter;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    @DisplayName("Deve retornar bad request para argumentos inválidos")
    void shouldHandleIllegalArgumentException() {
        MockHttpServletRequest request = request();

        var response = handler.handleIllegalArgumentException(
                new IllegalArgumentException("Invalid order"), request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid order", response.getBody().message());
        assertEquals("/api/v1/orders", response.getBody().path());
    }

    @Test
    @DisplayName("Deve retornar os erros de validação dos campos")
    void shouldHandleValidationException() throws NoSuchMethodException {
        BeanPropertyBindingResult bindingResult =
                new BeanPropertyBindingResult(new Object(), "request");
        bindingResult.addError(new FieldError(
                "request", "ticker", "", false, null, null, "O ticker é obrigatório"));
        MethodParameter parameter = new MethodParameter(
                ValidationController.class.getDeclaredMethod("createOrder", CreateOrderRequest.class), 0);
        MethodArgumentNotValidException exception =
                new MethodArgumentNotValidException(parameter, bindingResult);

        var response = handler.handleMethodArgumentNotValidException(exception, request());

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Erro de validação nos campos da requisição", response.getBody().message());
        assertEquals(List.of("Campo 'ticker': 'O ticker é obrigatório'"), response.getBody().details());
        assertEquals("/api/v1/orders", response.getBody().path());
    }

    @Test
    @DisplayName("Deve retornar erro interno para exceções inesperadas")
    void shouldHandleUnexpectedException() {
        var response = handler.handleGenericException(new Exception("Unexpected"), request());

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Ocorreu um erro interno inesperado no servidor", response.getBody().message());
        assertEquals("/api/v1/orders", response.getBody().path());
    }

    private MockHttpServletRequest request() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/api/v1/orders");
        return request;
    }

    private static class ValidationController {
        void createOrder(CreateOrderRequest request) {
        }
    }
}
