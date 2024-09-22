package cz.cvut.fel.event;

import cz.cvut.fel.dto.OrderItemDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Event representing ordered items in an order.
 *
 * Contains a list of order items associated with an order.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class OrderedItemsEvent implements Event{
    private UUID eventId = UUID.randomUUID();
    private Date eventDate = new Date();
    private List<OrderItemDto> orderItemDtos;

    @Override
    public UUID getEventId() {
        return this.eventId;
    }

    @Override
    public Date getDate() {
        return this.eventDate;
    }
}
