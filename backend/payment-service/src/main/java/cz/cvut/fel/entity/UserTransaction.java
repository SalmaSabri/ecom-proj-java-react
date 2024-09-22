package cz.cvut.fel.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Entity representing a user transaction in the system.
 *
 * This entity stores the details of a transaction made by a user, including the associated order ID,
 * user ID, and the transaction amount. Transactions are recorded whenever a user makes a purchase or
 * any balance-related operation.
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long orderId;
    private String userId;
    private BigDecimal amount;

    public UserTransaction(Long orderId, String userId, BigDecimal amount) {
        this.orderId = orderId;
        this.userId = userId;
        this.amount = amount;
    }
}
