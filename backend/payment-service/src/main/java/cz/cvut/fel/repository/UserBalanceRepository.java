package cz.cvut.fel.repository;

import cz.cvut.fel.entity.UserBalance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing {@link UserBalance} entities.
 *
 * This interface extends {@link JpaRepository} to provide basic CRUD operations for
 * {@link UserBalance} entities and includes custom query methods for user-specific operations.
 */
@Repository
public interface UserBalanceRepository extends JpaRepository<UserBalance, Long> {

    /**
     * Finds a {@link UserBalance} entity by the associated user ID.
     *
     * @param userId the ID of the user whose balance is being retrieved.
     * @return an {@link Optional} containing the {@link UserBalance} if found, or empty if not found.
     */
    Optional<UserBalance> findByUserId(String userId);
}
