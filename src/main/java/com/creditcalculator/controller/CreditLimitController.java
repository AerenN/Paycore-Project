package com.creditcalculator.controller;

import com.creditcalculator.exception.ForbiddenException;
import com.creditcalculator.models.CreditLimit;
import com.creditcalculator.services.CreditLimitCalculationService;
import com.creditcalculator.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/credit")
public class CreditLimitController {

    @Autowired
    private CreditLimitCalculationService creditLimitCalculationService;

    // When user requests a credit, does calculation and saves request to database.
    @GetMapping(path = "/request-credit")
    public ResponseEntity<?> requestCredit(@RequestParam String username){
        if (!username.equals(getPrincipal().getUsername()))
        {
            throw new ForbiddenException("Users can make requests only for their own accounts");
        }
        return ResponseEntity.ok(creditLimitCalculationService.requestCreditLimit(username));
    }

    // User can check credit limit amount after request.
    @GetMapping(path = "/check-credit-request")
    public List<CreditLimit> getApprovedCreditLimit(@RequestParam String username){
        if (!username.equals(getPrincipal().getUsername()))
        {
            throw new ForbiddenException("Users can make requests only for their own accounts");
        }
        return creditLimitCalculationService.getApprovedCreditLimits(username);
    }

    private UserDetailsImpl getPrincipal(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (UserDetailsImpl) auth.getPrincipal();
    }
}
