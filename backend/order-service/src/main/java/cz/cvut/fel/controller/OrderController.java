package cz.cvut.fel.controller;

import cz.cvut.fel.dto.OrderDto;
import cz.cvut.fel.entity.PurchaseOrder;
import cz.cvut.fel.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final HttpServletRequest request;
    @PostMapping("/create")
    @PreAuthorize("hasRole('USER')")
    public OrderDto createOrder(@RequestBody OrderDto orderDto) {
        // Get the current user's Keycloak principal
        Principal principal = request.getUserPrincipal();
        String userId = principal.getName();
        orderDto.setUserId(userId);
        return orderService.createOrder(orderDto);
    }

    @GetMapping
    public List<PurchaseOrder> getOrders(){
        return orderService.getAllOrders();
    }
}
