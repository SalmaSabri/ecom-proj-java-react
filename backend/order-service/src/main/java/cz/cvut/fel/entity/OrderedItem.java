package cz.cvut.fel.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Entity representing an ordered item within a purchase order.
 *
 * This entity holds details about an item that has been ordered, such as the product ID, name, quantity, and price.
 */
@Entity
@Table(name = "ordered_item_tbl")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderedItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "Order ID is required")
    private Long orderId;
    @NotBlank(message = "Product ID is required")
    private String productId;
    @NotBlank(message = "Product name is required")
    private String productName;
    @NotNull(message = "Quantity is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Quantity must be greater than zero")
    private BigDecimal quantity;
    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than zero")
    private BigDecimal price;
}
