package cz.cvut.fel.repository;

import cz.cvut.fel.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing {@link Product} entities in the MySQL database.
 *
 * This interface extends {@link JpaRepository} and provides basic CRUD operations for
 * {@link Product} entities, including saving, finding, updating, and deleting products
 * by their unique ID.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
}