package com.creditcalculator.services;

import com.creditcalculator.models.CreditLimit;
import com.creditcalculator.models.User;
import com.creditcalculator.repository.CreditLimitRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreditLimitCalculationServiceTest {

    private static final String USERNAME = "12345678902";
    private CreditLimitCalculationService creditLimitCalculationService;

    @Mock
    CreditLimitRepository creditLimitRepository;

    @Mock
    private CreditScoreService creditScoreService;

    @Mock
    private UserService userService;

    @Mock
    private RabbitTemplate rabbitTemplate;


    @BeforeEach
    void setUp() {
        creditLimitCalculationService = new CreditLimitCalculationService(creditLimitRepository, creditScoreService, userService, rabbitTemplate);

    }

    /**
     * If Credit Score is above 1000, credit limit is equal to monthly income power credit multiplier.
     * If credit score is less than 1000 and higher than 500, and income is more than 5000 - then credit limit is 20.000.
     * If credit score is less than 1000 and higher than 500, and income is lower than 5000 - then credit limit is 10.000.
     * If credit score is less than 500 then your credit request will be rejected.
     *
     */
    @Test
    void testCreditLimitCalculation_case1(){
        double creditLimit = creditLimitCalculationService.calculateCreditLimit(4100.0, 1250);
        assertThat(creditLimit).isEqualTo(16400.0);

    }

    @Test
    void testCreditLimitCalculation_case2(){
        double creditLimit = creditLimitCalculationService.calculateCreditLimit(6100.0, 750);
        assertThat(creditLimit).isEqualTo(20000.0);

    }

    @Test
    void testCreditLimitCalculation_case3(){
        double creditLimit = creditLimitCalculationService.calculateCreditLimit(3100.0, 750);
        assertThat(creditLimit).isEqualTo(10000.0);

    }

    @Test
    void testCreditLimitCalculation_case4(){
        double creditLimit = creditLimitCalculationService.calculateCreditLimit(6100.0, 250);
        assertThat(creditLimit).isEqualTo(0.);

    }

    @Test
    void testRequestCreditLimit_creditLimitExists(){

        when(creditLimitRepository.findAllByUsername(USERNAME)).thenReturn(List.of(new CreditLimit(USERNAME,1250,4100.0,true)));
        boolean creditLimitResult = creditLimitCalculationService.requestCreditLimit(USERNAME);
        assertThat(creditLimitResult).isFalse();

        verify(creditLimitRepository,times(0)).save(any());

    }

    @Test
    void testRequestCreditLimit_newCreditLimitSaved(){
        when(userService.getUserByUsername(USERNAME)).thenReturn(new User("Bob",USERNAME,"telphone Number",5040.0, "password"));
        when(creditScoreService.getRandomCreditScore()).thenReturn(1500);
        when(creditLimitRepository.findAllByUsername(USERNAME)).thenReturn(Collections.emptyList());
        boolean creditLimitResult = creditLimitCalculationService.requestCreditLimit(USERNAME);
        assertThat(creditLimitResult).isTrue();

        verify(creditLimitRepository,times(1)).save(any());
    }

    @Test
    void testRequestCreditLimit_rejected(){
        when(userService.getUserByUsername(USERNAME)).thenReturn(new User("Bob",USERNAME,"telphone Number",5040.0, "password"));
        when(creditScoreService.getRandomCreditScore()).thenReturn(150);
        when(creditLimitRepository.findAllByUsername(USERNAME)).thenReturn(Collections.emptyList());
        boolean creditLimitResult = creditLimitCalculationService.requestCreditLimit(USERNAME);
        assertThat(creditLimitResult).isFalse();

        verify(creditLimitRepository,times(1)).save(any());
    }

    @Test
    void testGetApprovedCreditLimit(){
        CreditLimit creditLimit = new CreditLimit(USERNAME,1250,4100.0,true);
        when(creditLimitRepository.findAllByUsername(USERNAME)).thenReturn(List.of(creditLimit));
        List<CreditLimit> approvedCreditLimits = creditLimitCalculationService.getApprovedCreditLimits(USERNAME);
        assertThat(approvedCreditLimits).contains(creditLimit).hasSize(1);
    }


}