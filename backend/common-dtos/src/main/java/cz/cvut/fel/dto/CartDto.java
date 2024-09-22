package cz.cvut.fel.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


/**
 * Data Transfer Object representing a shopping cart.
 *
 * Contains information about the cart, including the cart ID, the user ID of the cart owner,
 * and a list of cart items.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartDto {
    private Long cartId;
    private String userId;
    private List<CartItemDto> cartItemDtos;
}
