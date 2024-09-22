package cz.cvut.fel.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

/**
 * Event representing an action related to a shopping cart.
 *
 * Contains details about the user, product, and cart-related operations, such as adding a product to the cart.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartEvent implements Event{
    private UUID eventId = UUID.randomUUID();
    private Date eventDate = new Date();
    private String userId;
    private String productId;
    private String productName;
    private BigDecimal quantity;
    private BigDecimal price;

    @Override
    public UUID getEventId() {
        return this.eventId;
    }

    @Override
    public Date getDate() {
        return this.eventDate;
    }
}
