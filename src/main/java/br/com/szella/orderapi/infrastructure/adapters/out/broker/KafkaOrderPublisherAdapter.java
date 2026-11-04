package br.com.szella.orderapi.infrastructure.adapters.out.broker;

import br.com.szella.orderapi.application.ports.out.OrderEventPublisherPort;
import br.com.szella.orderapi.domain.model.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaOrderPublisherAdapter implements OrderEventPublisherPort {

    private static final String TOPIC = "order-created-events";
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void publishOrderCreated(Order order) {
        kafkaTemplate.send(TOPIC, order.getId().toString(), order);
    }
}
