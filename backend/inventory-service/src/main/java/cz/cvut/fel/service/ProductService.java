package cz.cvut.fel.service;

import cz.cvut.fel.dto.ProductDto;
import cz.cvut.fel.entity.Product;
import cz.cvut.fel.mapper.Mapper;
import cz.cvut.fel.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
