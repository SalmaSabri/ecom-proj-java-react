package cz.cvut.fel.service;

import cz.cvut.fel.dto.OrderDto;
import cz.cvut.fel.entity.Cart;
import cz.cvut.fel.entity.CartItem;
import cz.cvut.fel.event.CartEventDto;
import cz.cvut.fel.repository.CartItemRepository;
import cz.cvut.fel.repository.CartRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final CartProducer cartProducer;
    private final OrderServiceClient orderServiceClient;


    @Transactional
    public void addProductToCart(CartEventDto cartEventDto) {
        cartProducer.sendCartEvent(cartEventDto);  // Send the event to inventory service
    }

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


}
