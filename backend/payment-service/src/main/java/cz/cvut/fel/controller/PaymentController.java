package cz.cvut.fel.controller;

import cz.cvut.fel.dto.UserBalanceDto;
import cz.cvut.fel.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.HttpServletRequest;

import java.security.Principal;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;
    private final HttpServletRequest request;

    //create balance
    @PostMapping("/create")
    @PreAuthorize("hasRole('USER')")
    public UserBalanceDto createUserBalance(@RequestBody UserBalanceDto userBalanceDto) {
        Principal principal = request.getUserPrincipal();
        String userId = principal.getName();
        userBalanceDto.setUserId(userId);
        return paymentService.createBalance(userBalanceDto);
    }


    // add money (top-up balance)
    @PostMapping("/topup")
    @PreAuthorize("hasRole('USER')")
    public UserBalanceDto addMoney(@RequestBody UserBalanceDto userBalanceDto) {
        Principal principal = request.getUserPrincipal();
        String userId = principal.getName();
        userBalanceDto.setUserId(userId);
        return paymentService.topUpBalance(userBalanceDto);
    }

}
