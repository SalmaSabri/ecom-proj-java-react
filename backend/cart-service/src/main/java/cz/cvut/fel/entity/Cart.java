package cz.cvut.fel.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity representing a shopping cart in the system.
 *
 * Each cart is associated with a specific user and contains one or more {@link CartItem} entries.
 * The cart entity stores the user ID to associate the cart with a specific user.
 */
@Entity
@Table(name = "cart")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userId;
}
