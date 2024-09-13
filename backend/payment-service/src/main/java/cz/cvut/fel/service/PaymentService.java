package cz.cvut.fel.service;


import cz.cvut.fel.dto.OrderRequestDto;
import cz.cvut.fel.dto.PaymentRequestDto;
import cz.cvut.fel.entity.UserBalance;
import cz.cvut.fel.entity.UserTransaction;
import cz.cvut.fel.event.OrderEvent;
import cz.cvut.fel.event.PaymentEvent;
import cz.cvut.fel.repository.UserBalanceRepository;
import cz.cvut.fel.repository.UserTransactionRepository;
import cz.cvut.fel.status.PaymentStatus;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class PaymentService {

    private UserBalanceRepository userBalanceRepository;

    private UserTransactionRepository userTransactionRepository;


    @PostConstruct
    public void initUserBalanceInDB() {
        userBalanceRepository.saveAll(Stream.of(new UserBalance(101, 5000),
                new UserBalance(102, 3000),
                new UserBalance(103, 4200),
                new UserBalance(104, 20000),
                new UserBalance(105, 999)).collect(Collectors.toList()));
    }

    /**
     * // get the user id
     * // check the balance availability
     * // if balance sufficient -> Payment completed and deduct amount from DB
     * // if payment not sufficient -> cancel order event and update the amount in DB
     **/
    @Transactional
    public PaymentEvent newOrderEvent(OrderEvent orderEvent) {
        OrderRequestDto orderRequestDto = orderEvent.getOrderRequestDto();

        PaymentRequestDto paymentRequestDto = new PaymentRequestDto(orderRequestDto.getOrderId(),
                orderRequestDto.getUserId(), orderRequestDto.getAmount());

        return userBalanceRepository.findById(orderRequestDto.getUserId())
                .filter(ub -> ub.getPrice() > orderRequestDto.getAmount())
                .map(ub -> {
                    ub.setPrice(ub.getPrice() - orderRequestDto.getAmount());
                    userTransactionRepository.save(new UserTransaction(orderRequestDto.getOrderId(), orderRequestDto.getUserId(), orderRequestDto.getAmount()));
                    return new PaymentEvent(paymentRequestDto, PaymentStatus.PAYMENT_COMPLETED);
                }).orElse(new PaymentEvent(paymentRequestDto, PaymentStatus.PAYMENT_FAILED));

    }

    @Transactional
    public void cancelOrderEvent(OrderEvent orderEvent) {

        userTransactionRepository.findById(orderEvent.getOrderRequestDto().getOrderId())
                .ifPresent(ut -> {
                    userTransactionRepository.delete(ut);
                    userBalanceRepository.findById(ut.getUserId())
                            .ifPresent(ub -> ub.setPrice(ub.getPrice() + ut.getAmount()));
                });

    }
}
