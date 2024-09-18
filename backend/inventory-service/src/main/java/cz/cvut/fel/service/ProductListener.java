package cz.cvut.fel.service;

import cz.cvut.fel.event.CartEventDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProductListener {

    private final ProductService productService;

    @KafkaListener(topics = "cart-topic", groupId = "cart-event-group")
    public void handleCartEvent(CartEventDto cartEventDto) {
        log.info("Received cart event for product ID: {}", cartEventDto.getProductId());
        // Check product availability or update stock here
        productService.checkProductAvailability(cartEventDto.getProductId(), cartEventDto.getQuantity());
    }
}
