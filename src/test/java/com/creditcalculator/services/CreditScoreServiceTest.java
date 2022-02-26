package com.creditcalculator.services;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CreditScoreServiceTest {

    private static CreditScoreService creditScoreService;

    @BeforeAll
    public static void before(){
        creditScoreService = new CreditScoreService();
    }

    @Test
    public void testRandomCreditScore(){
        Integer randomCreditScore = creditScoreService.getRandomCreditScore();
        assertThat(randomCreditScore).isIn(CreditScoreService.RAND_CREDIT_SCORE_LIST);
    }

}