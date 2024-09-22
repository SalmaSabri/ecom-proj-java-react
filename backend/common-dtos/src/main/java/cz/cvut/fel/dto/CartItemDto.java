package cz.cvut.fel.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


/**
 * Data Transfer Object representing an item in the shopping cart.
 *
 * Contains product details, including product ID, product name, quantity, and price.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItemDto {
    private String productId;
    private String productName;
    private BigDecimal quantity;
    private BigDecimal price;
}
