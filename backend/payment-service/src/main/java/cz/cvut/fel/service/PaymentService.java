package cz.cvut.fel.service;


import cz.cvut.fel.dto.OrderDto;
import cz.cvut.fel.dto.PaymentDto;
import cz.cvut.fel.dto.UserBalanceDto;
import cz.cvut.fel.entity.UserTransaction;
import cz.cvut.fel.event.OrderEvent;
import cz.cvut.fel.event.PaymentEvent;
import cz.cvut.fel.mapper.Mapper;
import cz.cvut.fel.repository.UserBalanceRepository;
import cz.cvut.fel.repository.UserTransactionRepository;
import cz.cvut.fel.status.PaymentStatus;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class PaymentService {

    private UserBalanceRepository userBalanceRepository;

    private UserTransactionRepository userTransactionRepository;



    /**
     * // get the user id
     * // check the balance availability
     * // if balance sufficient -> Payment completed and deduct amount from DB
     * // if payment not sufficient -> cancel order event and update the amount in DB
     **/
    @Transactional
    public PaymentEvent newOrderEvent(OrderEvent orderEvent) {
        OrderDto orderDto = orderEvent.getOrderDto();

        PaymentDto paymentDto = new PaymentDto(orderDto.getOrderId(),
                orderDto.getUserId(), orderDto.getAmount());

        return userBalanceRepository.findByUserId(orderDto.getUserId())
                .filter(ub -> ub.getPrice().compareTo(orderDto.getAmount()) > 0)
                .map(ub -> {
                    ub.setPrice(ub.getPrice().subtract(orderDto.getAmount()));
                    userTransactionRepository.save(new UserTransaction(orderDto.getOrderId(), orderDto.getUserId(), orderDto.getAmount()));
                    return new PaymentEvent(paymentDto, PaymentStatus.PAYMENT_COMPLETED);
                }).orElse(new PaymentEvent(paymentDto, PaymentStatus.PAYMENT_FAILED));

    }

    @Transactional
    public void cancelOrderEvent(OrderEvent orderEvent) {

        userTransactionRepository.findById(orderEvent.getOrderDto().getOrderId())
                .ifPresent(ut -> {
                    userTransactionRepository.delete(ut);
                    userBalanceRepository.findByUserId(ut.getUserId())
                            .ifPresent(ub -> ub.setPrice(ub.getPrice().add(ut.getAmount())));
                });

    }

    public UserBalanceDto createBalance(UserBalanceDto userBalanceDto) {
        if (userBalanceDto.getPrice() == null) {
            userBalanceDto.setPrice(BigDecimal.ZERO);
        }
        var ub = Mapper.convertDtoToEntity(userBalanceDto);
        var savedUb = userBalanceRepository.save(ub);
        return Mapper.convertEntityToDto(savedUb);
    }

    @Transactional
    public UserBalanceDto topUpBalance(UserBalanceDto userBalanceDto) {
        return userBalanceRepository.findByUserId(userBalanceDto.getUserId())
                .map(existingBalance -> {
                    existingBalance.setPrice(existingBalance.getPrice().add(userBalanceDto.getPrice()));
                    var updatedBalance = userBalanceRepository.save(existingBalance);
                    return Mapper.convertEntityToDto(updatedBalance);
                })
                .orElseThrow(() -> new IllegalArgumentException("User balance not found"));
    }
}
