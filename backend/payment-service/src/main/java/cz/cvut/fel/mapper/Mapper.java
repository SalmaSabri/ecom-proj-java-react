package cz.cvut.fel.mapper;

import cz.cvut.fel.dto.UserBalanceDto;
import cz.cvut.fel.entity.UserBalance;

public class Mapper {

    public static UserBalance convertDtoToEntity(UserBalanceDto dto) {
        UserBalance userBalance = new UserBalance();
        userBalance.setUserId(dto.getUserId());
        userBalance.setPrice(dto.getPrice());
        return userBalance;
    }

    public static UserBalanceDto convertEntityToDto(UserBalance ub) {
        UserBalanceDto dto = new UserBalanceDto();
        dto.setUserId(ub.getUserId());
        dto.setPrice(ub.getPrice());
        return dto;
    }
}
