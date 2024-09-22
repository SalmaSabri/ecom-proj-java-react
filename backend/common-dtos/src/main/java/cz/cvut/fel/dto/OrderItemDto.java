package cz.cvut.fel.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Data Transfer Object representing an item in an order.
 *
 * Contains product details, including product ID, product name, quantity, price, and the order ID it belongs to.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItemDto {
    private String productId;
    private Long orderId;
    private String productName;
    private BigDecimal quantity;
    private BigDecimal price;
}

