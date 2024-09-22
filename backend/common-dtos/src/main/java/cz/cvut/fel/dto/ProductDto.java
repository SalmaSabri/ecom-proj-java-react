package cz.cvut.fel.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Data Transfer Object representing a product.
 *
 * Contains product details such as ID, name, description, price, and quantity.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductDto {
    private String id;
    private String name;
    private String description;
    private BigDecimal price;
    private BigDecimal quantity;
}
