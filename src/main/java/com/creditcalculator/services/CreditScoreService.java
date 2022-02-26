package com.creditcalculator.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Slf4j
@Service
public class CreditScoreService {

    static final List<Integer> RAND_CREDIT_SCORE_LIST = Arrays.asList(350, 750, 1250);

    public Integer getRandomCreditScore(){


        Random rand = new Random();
        int randomCreditScore = RAND_CREDIT_SCORE_LIST.get(rand.nextInt(RAND_CREDIT_SCORE_LIST.size()));
        log.info("Random credit score set to: " + randomCreditScore);
        return randomCreditScore;
    }


    public Integer getCalculatedCreditScore(){
        return null;
    }
}
