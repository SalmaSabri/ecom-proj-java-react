package cz.cvut.fel.service;

import cz.cvut.fel.event.CartEventDto;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CartProducer {
    private final KafkaTemplate<String, CartEventDto> kafkaTemplate;

    public void sendCartEvent(CartEventDto event) {
        kafkaTemplate.send("cart-topic", event);
    }
}
