package cz.cvut.fel.controller;

import cz.cvut.fel.dto.CartDto;
import cz.cvut.fel.event.CartEvent;
import cz.cvut.fel.service.CartService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * REST controller for handling cart-related operations.
 *
 * This controller manages adding items to the cart, retrieving the cart for the
 * authenticated user, and submitting the cart for checkout. Access to the endpoints
 * is restricted to users with the 'USER' role.
 */
@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final HttpServletRequest request;

    /**
     * Adds a product to the authenticated user's cart.
     *
     * The user must have the 'USER' role to perform this operation. The user's ID is
     * automatically set based on the authenticated principal.
     *
     * @param cartEvent the DTO containing the product information to add to the cart.
     * @return a {@link ResponseEntity} indicating the operation was successful.
     */
    @PostMapping("/add")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Void> addToCart(@RequestBody CartEvent cartEvent) {
        Principal principal = request.getUserPrincipal();
        String userId = principal.getName();
        cartEvent.setUserId(userId);
        cartService.addProductToCart(cartEvent);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Fetches the current cart of the authenticated user.
     *
     * The user must have the 'USER' role to access their cart.
     *
     * @return a {@link ResponseEntity} containing the cart details as a {@link CartDto}.
     */
    @GetMapping("/get")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CartDto> getUserCart() {
        Principal principal = request.getUserPrincipal();
        String userId = principal.getName();
        CartDto cartDto = cartService.getUserCart(userId);
        return new ResponseEntity<>(cartDto, HttpStatus.OK);

    }

    /**
     * Submits the authenticated user's cart for checkout.
     *
     * The user must have the 'USER' role to submit their cart.
     *
     * @return a {@link ResponseEntity} indicating the operation was successful.
     */
    @PostMapping("/submit")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Void> submitCart() {
        Principal principal = request.getUserPrincipal();
        String userId = principal.getName();
        cartService.submitCart(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
