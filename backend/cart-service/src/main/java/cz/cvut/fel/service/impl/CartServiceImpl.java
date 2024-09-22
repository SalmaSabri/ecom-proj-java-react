package cz.cvut.fel.service.impl;

import cz.cvut.fel.dto.CartDto;
import cz.cvut.fel.dto.CartItemDto;
import cz.cvut.fel.dto.OrderDto;
import cz.cvut.fel.entity.Cart;
import cz.cvut.fel.entity.CartItem;
import cz.cvut.fel.event.CartEvent;
import cz.cvut.fel.feign.OrderServiceClient;
import cz.cvut.fel.kafka.CartProducer;
import cz.cvut.fel.mapper.Mapper;
import cz.cvut.fel.repository.CartItemRepository;
import cz.cvut.fel.repository.CartRepository;
import cz.cvut.fel.service.CartService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Service implementation for managing cart-related operations.
 */
@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final CartProducer cartProducer;
    private final OrderServiceClient orderServiceClient;


    @Override
    @Transactional
    public void addProductToCart(CartEvent cartEvent) {
        cartProducer.publishCartEvent(cartEvent);  // Send the event to inventory service
    }

    @Override
    @Transactional
    public void submitCart(String userId) {
        // Fetch the cart by userId
        Optional<Cart> cartOptional = cartRepository.findByUserId(userId);
        if (cartOptional.isPresent()) {
            Cart cart = cartOptional.get();
            List<CartItem> cartItems = cartItemRepository.findByCartId(cart.getId());

            // Create an OrderDto from the cart items
            OrderDto orderDto = new OrderDto();
            orderDto.setUserId(userId);

            // Calculate total price
            BigDecimal totalPrice = cartItems.stream()
                    .map(cartItem -> cartItem.getPrice().multiply(cartItem.getQuantity()))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            orderDto.setAmount(totalPrice);  // Set the total price in OrderDto

            // Call the order service to create the order
            orderServiceClient.createOrder(orderDto);

        } else {
            throw new RuntimeException("No cart found for user: " + userId);
        }
    }

    @Override
    public CartDto getUserCart(String userId) {
        Optional<Cart> cartOptional = cartRepository.findByUserId(userId);
        if (cartOptional.isPresent()) {
            Cart cart = cartOptional.get();
            List<CartItem> cartItems = cartItemRepository.findByCartId(cart.getId());
            List<CartItemDto> cartItemDtos = cartItems.stream()
                    .map(Mapper::toDto)
                    .toList();

            return CartDto.builder()
                    .cartId(cart.getId())
                    .userId(userId)
                    .cartItemDtos(cartItemDtos)
                    .build();
        } else {
            throw new RuntimeException("No cart found for user: " + userId);
        }
    }
}
