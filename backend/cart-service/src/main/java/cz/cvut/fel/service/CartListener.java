package cz.cvut.fel.service;

import cz.cvut.fel.dto.OrderItemDto;
import cz.cvut.fel.entity.Cart;
import cz.cvut.fel.entity.CartItem;
import cz.cvut.fel.event.CartEventDto;
import cz.cvut.fel.event.HistoryEvent;
import cz.cvut.fel.repository.CartItemRepository;
import cz.cvut.fel.repository.CartRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CartListener {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    private final CartProducer cartProducer;

    @KafkaListener(topics = "inventory-topic", groupId = "inventory-event-group")
    public void inventoryEventConsumer(CartEventDto cartEventDto) {
        // Find cart by userId (for simplicity, assume one cart per user)
        Optional<Cart> cartOptional = cartRepository.findByUserId(cartEventDto.getUserId());
        Cart cart;
        if (cartOptional.isPresent()) {
            cart = cartOptional.get();
        } else {
            cart = Cart.builder()
                    .userId(cartEventDto.getUserId())
                    .build();
            cart = cartRepository.save(cart);
        }

        // Create a CartItem and associate it with the Cart via cartId
        CartItem cartItem = CartItem.builder()
                    .cartId(cart.getId())
                    .productId(cartEventDto.getProductId())
                    .productName(cartEventDto.getProductName())
                    .quantity(cartEventDto.getQuantity())
                    .price(cartEventDto.getPrice())
                    .build();

        cartItemRepository.save(cartItem);
    }

    @KafkaListener(topics = "history-topic", groupId = "order-history-event-group")
    @Transactional
    public void orderEventConsumer(HistoryEvent historyEvent) {
        Optional<Cart> cartOptional = cartRepository.findByUserId(historyEvent.getUserId());
        if (cartOptional.isPresent()) {
            Cart cart = cartOptional.get();
            List<CartItem> cartItems = cartItemRepository.findByCartId(cart.getId());
            List<OrderItemDto> orderItems = cartItems.stream().map(cartItem -> OrderItemDto.builder()
                            .orderId(historyEvent.getOrderId())
                            .productId(cartItem.getProductId())
                            .productName(cartItem.getProductName())
                            .quantity(cartItem.getQuantity())
                            .price(cartItem.getPrice())
                    .build()).toList();

            HistoryEvent historyEventWithOrderedItems = HistoryEvent.builder()
                            .orderId(historyEvent.getOrderId())
                            .orderItemDtos(orderItems)
                            .userId(historyEvent.getUserId())
                                    .build();

            cartProducer.publishHistoryEvent(historyEventWithOrderedItems);

            // Optionally clear the cart after processing the order
             cartItemRepository.deleteByCartId(cart.getId());
        } else {
            throw new RuntimeException("No cart found for user: " + historyEvent.getUserId());
        }
    }

}
