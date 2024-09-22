package cz.cvut.fel.entity;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

/**
 * Entity representing a product in the system.
 *
 * This entity is mapped to a MongoDB collection named "product". It contains information about
 * the product such as its ID, name, description, price, and quantity.
 */
@Document(value = "product")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Product {

    @Id
    private String id;
    @NotBlank(message = "Product name is required")
    @Size(min = 2, max = 100, message = "Product name must be between 2 and 100 characters")
    private String name;
    @Size(max = 500, message = "Product description must not exceed 500 characters")
    private String description;
    @NotNull(message = "Product price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Product price must be greater than zero")
    private BigDecimal price;
    @NotNull(message = "Product quantity is required")
    @DecimalMin(value = "0.0", message = "Product quantity must not be negative")
    private BigDecimal quantity;
}
