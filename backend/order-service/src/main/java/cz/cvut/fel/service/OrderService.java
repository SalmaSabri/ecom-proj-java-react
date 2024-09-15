package cz.cvut.fel.service;

import cz.cvut.fel.dto.OrderDto;
import cz.cvut.fel.entity.PurchaseOrder;
import cz.cvut.fel.mapper.Mapper;
import cz.cvut.fel.repository.OrderRepository;
import cz.cvut.fel.status.OrderStatus;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderStatusPublisher orderStatusPublisher;

    @Transactional
    public OrderDto createOrder(OrderDto orderDto) {
        PurchaseOrder order = orderRepository.save(Mapper.convertDtoToEntity(orderDto));
        orderDto.setOrderId(order.getId());
        //produce kafka event with status ORDER_CREATED
        orderStatusPublisher.publishOrderEvent(orderDto, OrderStatus.ORDER_CREATED);
        return Mapper.convertEntityToDto(order);
    }

    public List<PurchaseOrder> getAllOrders(){
        return orderRepository.findAll();
    }

}
