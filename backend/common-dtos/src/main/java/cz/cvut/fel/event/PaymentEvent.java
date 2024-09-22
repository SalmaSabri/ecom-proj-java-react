package cz.cvut.fel.event;

import cz.cvut.fel.dto.PaymentDto;
import cz.cvut.fel.status.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

/**
 * Event representing a payment transaction.
 *
 * Contains payment details and the status of the payment.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PaymentEvent implements Event{

    private UUID eventId = UUID.randomUUID();
    private Date eventDate = new Date();
    private PaymentDto paymentDto;
    private PaymentStatus paymentStatus;

    @Override
    public UUID getEventId() {
        return this.eventId;
    }

    @Override
    public Date getDate() {
        return this.eventDate;
    }

    public PaymentEvent(PaymentDto paymentDto, PaymentStatus paymentStatus) {
        this.paymentDto = paymentDto;
        this.paymentStatus = paymentStatus;
    }
}
