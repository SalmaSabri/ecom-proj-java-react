package cz.cvut.fel.controller;

import cz.cvut.fel.dto.OrderDto;
import cz.cvut.fel.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

/**
 * REST controller for managing orders.
 *
 * This controller provides endpoints to create and retrieve orders for the authenticated user.
 */
@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final HttpServletRequest request;

    /**
     * Creates a new order for the authenticated user.
     *
     * This endpoint is restricted to users with the 'USER' role. It creates an order based on the provided
     * {@link OrderDto} and associates it with the current authenticated user.
     *
     * @param orderDto the {@link OrderDto} containing order details.
     * @return a {@link ResponseEntity} containing the created {@link OrderDto} and HTTP status 201 (Created).
     */
    @PostMapping("/create")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderDto orderDto) {
        Principal principal = request.getUserPrincipal();
        String userId = principal.getName();
        orderDto.setUserId(userId);
        OrderDto newOrderDto = orderService.createOrder(orderDto);
        return new ResponseEntity<>(newOrderDto, HttpStatus.CREATED);
    }

    /**
     * Retrieves all orders for the authenticated user.
     *
     * This endpoint is restricted to users with the 'USER' role. It fetches all orders associated with
     * the current authenticated user.
     *
     * @return a {@link ResponseEntity} containing a list of {@link OrderDto} objects and HTTP status 200 (OK).
     */
    @GetMapping("/get")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<OrderDto>> getOrders(){
        Principal principal = request.getUserPrincipal();
        String userId = principal.getName();
        List<OrderDto> orderDtos = orderService.getAllOrders(userId);
        return new ResponseEntity<>(orderDtos, HttpStatus.OK);
    }
}
