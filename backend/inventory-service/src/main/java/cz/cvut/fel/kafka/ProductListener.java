package cz.cvut.fel.kafka;

import cz.cvut.fel.entity.Product;
import cz.cvut.fel.event.CartEvent;
import cz.cvut.fel.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Kafka listener for processing cart events.
 *
 * This class listens for events on the "cart-topic" and checks the availability of products
 * or updates their stock. After processing, it forwards the event to the "inventory-topic".
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class ProductListener {

    private final ProductService productService;

    /**
     * Consumes a cart event from the "cart-topic", checks the availability of the product,
     * and returns a modified event to be sent to the "inventory-topic".
     *
     * @param cartEvent the {@link CartEvent} containing information about the cart and the product.
     * @return a modified {@link CartEvent} with updated product details.
     */
    @KafkaListener(topics = "cart-topic", groupId = "cart-event-group")
    @SendTo(value = "inventory-topic")
    @Transactional
    public CartEvent cartEventConsumer(CartEvent cartEvent) {
        log.info("Received cart event for product ID: {}", cartEvent.getProductId());
        // Check product availability or update stock here
        Product product = productService.checkProductAvailability(cartEvent.getProductId(), cartEvent.getQuantity());
        return CartEvent.builder()
                .userId(cartEvent.getUserId())
                .productId(cartEvent.getProductId())
                .productName(product.getName())
                .quantity(cartEvent.getQuantity())
                .price(product.getPrice())
                .build();
    }
}
