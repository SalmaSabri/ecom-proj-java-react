package cz.cvut.fel.service;


import cz.cvut.fel.dto.OrderDto;
import cz.cvut.fel.dto.PaymentDto;
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

import java.math.BigDecimal;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class PaymentService {

    private UserBalanceRepository userBalanceRepository;

    private UserTransactionRepository userTransactionRepository;


    @PostConstruct
    public void initUserBalanceInDB() {
        userBalanceRepository.deleteAll(); // Clear the table
        userBalanceRepository.saveAll(Stream.of(
                new UserBalance("101", new BigDecimal(5000)),
                new UserBalance("102", new BigDecimal(3000)),
                new UserBalance("103", new BigDecimal(4200)),
                new UserBalance("104", new BigDecimal(20000)),
                new UserBalance("57e2403c-3cd7-4312-819b-2de2d15eaaef", new BigDecimal(999))
        ).collect(Collectors.toList()));
    }

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
}
