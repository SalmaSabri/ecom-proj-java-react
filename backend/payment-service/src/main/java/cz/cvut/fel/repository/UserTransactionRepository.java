package cz.cvut.fel.repository;

import cz.cvut.fel.entity.UserTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing {@link UserTransaction} entities.
 *
 * This interface extends {@link JpaRepository} to provide basic CRUD operations for
 * {@link UserTransaction} entities, allowing interaction with the database to manage user transactions.
 */
@Repository
public interface UserTransactionRepository extends JpaRepository<UserTransaction, Long> {
}
