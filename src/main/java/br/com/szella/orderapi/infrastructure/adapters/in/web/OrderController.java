package br.com.szella.orderapi.infrastructure.adapters.in.web;

import br.com.szella.orderapi.application.ports.in.CreateOrderUseCase;
import br.com.szella.orderapi.application.ports.in.OrderResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final CreateOrderUseCase createOrderUseCase;

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody CreateOrderRequest request) {
        OrderResponse response = createOrderUseCase.execute(request.toCommand());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}
