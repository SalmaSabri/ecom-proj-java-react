package cz.cvut.fel.repository;

import cz.cvut.fel.entity.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing {@link Product} entities in the MongoDB database.
 *
 * This interface extends {@link MongoRepository} and provides basic CRUD operations for
 * {@link Product} entities, including saving, finding, updating, and deleting products
 * by their unique ID.
 */
@Repository
public interface ProductRepository extends MongoRepository<Product, String> {
}
