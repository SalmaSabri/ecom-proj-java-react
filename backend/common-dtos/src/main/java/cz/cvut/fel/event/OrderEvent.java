package cz.cvut.fel.event;

import cz.cvut.fel.dto.OrderDto;
import cz.cvut.fel.status.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderEvent implements Event{

    private UUID eventId = UUID.randomUUID();
    private Date eventDate = new Date();
    private OrderDto orderDto;
    private OrderStatus orderStatus;

    @Override
    public UUID getEventId() {
        return eventId;
    }

    @Override
    public Date getDate() {
        return eventDate;
    }

    public OrderEvent(OrderDto orderDto, OrderStatus orderStatus) {
        this.orderDto = orderDto;
        this.orderStatus = orderStatus;
    }
}
