package cz.cvut.fel.service;

import cz.cvut.fel.entity.Cart;
import cz.cvut.fel.entity.CartItem;
import cz.cvut.fel.event.CartEventDto;
import cz.cvut.fel.repository.CartItemRepository;
import cz.cvut.fel.repository.CartRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final CartProducer cartProducer;


    @Transactional
    public void addProductToCart(CartEventDto cartEventDto) {
        cartProducer.sendCartEvent(cartEventDto);  // Send the event to product service
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
        CartItem cartItem = new CartItem();
        cartItem.setCartId(cart.getId());
        cartItem.setProductId(cartEventDto.getProductId());
        cartItem.setProductName(cartEventDto.getProductName());
        cartItem.setQuantity(cartEventDto.getQuantity());
        cartItem.setPrice(cartEventDto.getPrice());

        cartItemRepository.save(cartItem);
    }
}
