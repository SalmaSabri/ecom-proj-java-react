package cz.cvut.fel.controller;

import cz.cvut.fel.entity.Cart;
import cz.cvut.fel.event.CartEventDto;
import cz.cvut.fel.repository.CartRepository;
import cz.cvut.fel.service.CartService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final CartRepository cartRepository;
    private final HttpServletRequest request;

    @PostMapping("/add")
    public ResponseEntity<Void> addToCart(@RequestBody CartEventDto cartEventDto) {
        Principal principal = request.getUserPrincipal();
        String userId = principal.getName();
        cartEventDto.setUserId(userId);
        cartService.addProductToCart(cartEventDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // Fetch the cart for the authenticated user
    @GetMapping("/me")
    public ResponseEntity<Cart> getCartForAuthenticatedUser() {
        Principal principal = request.getUserPrincipal();
        String userId = principal.getName();
        Optional<Cart> cart = cartRepository.findByUserId(userId);

        return cart.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/submit")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Void> submitCart() {
        Principal principal = request.getUserPrincipal();
        String userId = principal.getName();

        // Submit the cart for the authenticated user
        cartService.submitCart(userId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
