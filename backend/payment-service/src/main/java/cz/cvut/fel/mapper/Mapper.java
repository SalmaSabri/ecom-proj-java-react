package cz.cvut.fel.mapper;

import cz.cvut.fel.dto.UserBalanceDto;
import cz.cvut.fel.entity.UserBalance;

/**
 * Utility class responsible for converting between {@link UserBalanceDto} and {@link UserBalance} entities.
 *
 * This class provides static methods to map data transfer objects (DTOs) to entities and vice versa.
 */
public class Mapper {

    /**
     * Converts a {@link UserBalanceDto} to a {@link UserBalance} entity.
     *
     * This method maps the fields of a {@link UserBalanceDto} to the corresponding fields of
     * a {@link UserBalance} entity.
     *
     * @param dto the {@link UserBalanceDto} to be converted.
     * @return the corresponding {@link UserBalance} entity.
     */
    public static UserBalance convertDtoToEntity(UserBalanceDto dto) {
        return UserBalance.builder()
                .userId(dto.getUserId())
                .price(dto.getPrice())
                .build();
    }

    /**
     * Converts a {@link UserBalance} entity to a {@link UserBalanceDto}.
     *
     * This method maps the fields of a {@link UserBalance} entity to the corresponding fields of
     * a {@link UserBalanceDto}.
     *
     * @param ub the {@link UserBalance} entity to be converted.
     * @return the corresponding {@link UserBalanceDto}.
     */
    public static UserBalanceDto convertEntityToDto(UserBalance ub) {
        return UserBalanceDto.builder()
                .userId(ub.getUserId())
                .price(ub.getPrice())
                .build();
    }
}
