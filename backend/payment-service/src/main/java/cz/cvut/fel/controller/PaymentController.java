package cz.cvut.fel.controller;

import cz.cvut.fel.dto.UserBalanceDto;
import cz.cvut.fel.service.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * REST controller for managing user payment and balance-related operations.
 *
 * This controller provides endpoints for creating a user balance, topping up the balance,
 * and retrieving the current balance for the authenticated user.
 */
@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;
    private final HttpServletRequest request;

    /**
     * Creates a new balance for the authenticated user.
     *
     * This endpoint allows the authenticated user to create a balance if one does not exist.
     * The user ID is taken from the currently authenticated user's principal.
     *
     * @param userBalanceDto the details of the balance to be created.
     * @return a {@link ResponseEntity} containing the created {@link UserBalanceDto} and HTTP status 201 (Created).
     */
    @PostMapping("/create")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<UserBalanceDto> createUserBalance(@RequestBody UserBalanceDto userBalanceDto) {
        Principal principal = request.getUserPrincipal();
        String userId = principal.getName();
        userBalanceDto.setUserId(userId);
        UserBalanceDto newUBDto = paymentService.createBalance(userBalanceDto);
        return new ResponseEntity<>(newUBDto, HttpStatus.CREATED);
    }


    /**
     * Adds money to the authenticated user's balance (top-up).
     *
     * This endpoint allows the authenticated user to top-up their balance by adding a specified amount.
     * The user ID is taken from the currently authenticated user's principal.
     *
     * @param userBalanceDto the details of the balance and the amount to be added.
     * @return a {@link ResponseEntity} containing the updated {@link UserBalanceDto} and HTTP status 200 (OK).
     */
    @PostMapping("/topup")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<UserBalanceDto> addMoney(@RequestBody UserBalanceDto userBalanceDto) {
        Principal principal = request.getUserPrincipal();
        String userId = principal.getName();
        userBalanceDto.setUserId(userId);
        UserBalanceDto updatedUBDto = paymentService.topUpBalance(userBalanceDto);
        return new ResponseEntity<>(updatedUBDto, HttpStatus.OK);
    }

    /**
     * Retrieves the current balance of the authenticated user.
     *
     * This endpoint fetches the current balance for the authenticated user based on their user ID.
     *
     * @return a {@link ResponseEntity} containing the {@link UserBalanceDto} with the user's balance and HTTP status 200 (OK).
     */
    @GetMapping("/getBalance")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<UserBalanceDto> getBalance() {
        Principal principal = request.getUserPrincipal();
        String userId = principal.getName();
        UserBalanceDto ub = paymentService.getBalance(userId);
        return new ResponseEntity<>(ub, HttpStatus.OK);
    }

}
