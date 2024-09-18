package cz.cvut.fel.service;

import cz.cvut.fel.dto.ProductDto;
import cz.cvut.fel.entity.Product;
import cz.cvut.fel.mapper.Mapper;
import cz.cvut.fel.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    public ProductDto createProduct(ProductDto dto) {
        Product product = Mapper.convertToEntity(dto);

        Product savedProduct = productRepository.save(product);
        log.info("Product {} is saved", product.getId());
        return Mapper.convertToDto(savedProduct);
    }

    @Transactional
    public void checkProductAvailability(String productId, BigDecimal quantity) {
        Optional<Product> productOptional = productRepository.findById(productId);

        if (productOptional.isPresent()) {
            Product product = productOptional.get();

            // Check if there is enough stock
            if (product.getQuantity().compareTo(quantity) >= 0) {
                // Reduce the stock
                product.setQuantity(product.getQuantity().subtract(quantity));
                productRepository.save(product);
                log.info("Product {} stock updated, remaining quantity: {}", productId, product.getQuantity());
            } else {
                log.warn("Not enough stock for product {}", productId);
                throw new RuntimeException("Not enough stock available for product: " + productId);
            }
        } else {
            log.warn("Product {} not found", productId);
            throw new RuntimeException("Product not found: " + productId);
        }
    }
}
