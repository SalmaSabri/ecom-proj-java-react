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
 * Entity representing a user's balance in the system.
 *
 * This entity stores the balance amount associated with a user, including their user ID
 * and the available balance (price). The balance is managed and updated based on user transactions.
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserBalance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userId;
    private BigDecimal price;
}
