package cz.cvut.fel.mapper;

import cz.cvut.fel.dto.ProductDto;
import cz.cvut.fel.entity.Product;

/**
 * Utility class for converting between {@link Product} entity and {@link ProductDto}.
 *
 * This class provides static methods for mapping from entities to DTOs and vice versa,
 * facilitating data transfer between different layers of the application.
 */
public class Mapper {

    /**
     * Converts a {@link Product} entity to a {@link ProductDto}.
     *
     * This method maps all fields from the {@link Product} entity to the corresponding fields
     * in the {@link ProductDto}.
     *
     * @param product the {@link Product} entity to be converted.
     * @return the corresponding {@link ProductDto}.
     */
    public static ProductDto convertToDto(Product product) {
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .build();
    }

    /**
     * Converts a {@link ProductDto} to a {@link Product} entity.
     *
     * This method maps all fields from the {@link ProductDto} to the corresponding fields
     * in the {@link Product} entity, except for the product ID, which is typically generated
     * automatically by the persistence layer.
     *
     * @param dto the {@link ProductDto} to be converted.
     * @return the corresponding {@link Product} entity.
     */
    public static Product convertToEntity(ProductDto dto) {
        return Product.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .quantity(dto.getQuantity())
                .build();
    }
}
