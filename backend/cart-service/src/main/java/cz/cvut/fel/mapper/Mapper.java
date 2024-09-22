package cz.cvut.fel.mapper;

import cz.cvut.fel.dto.CartItemDto;
import cz.cvut.fel.entity.CartItem;

public class Mapper {
    /**
     * Converts a {@link CartItem} entity to a {@link CartItemDto}.
     *
     * @param cartItem the {@link CartItem} entity to be converted.
     * @return the corresponding {@link CartItemDto}.
     */
    public static CartItemDto toDto(CartItem cartItem) {
        return CartItemDto.builder()
                .productId(cartItem.getProductId())
                .productName(cartItem.getProductName())
                .quantity(cartItem.getQuantity())
                .price(cartItem.getPrice())
                .build();
    }
}
