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
 * Event representing a user's order history.
 *
 * Contains details about the user and their previous orders.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HistoryEvent implements Event{
    private UUID eventId = UUID.randomUUID();
    private Date eventDate = new Date();
    private String userId;
    private Long orderId;
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
