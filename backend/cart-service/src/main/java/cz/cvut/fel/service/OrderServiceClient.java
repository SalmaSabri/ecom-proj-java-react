package cz.cvut.fel.service;

import cz.cvut.fel.dto.OrderDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "order-service", configuration = FeignClientInterceptor.class)
public interface OrderServiceClient {

    @PostMapping("/order/create")
    OrderDto createOrder(@RequestBody OrderDto orderDto);
}
