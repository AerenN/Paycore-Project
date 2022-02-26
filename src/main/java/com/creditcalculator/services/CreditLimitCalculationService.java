package com.creditcalculator.services;

import com.creditcalculator.CreditApprovedEvent;
import com.creditcalculator.config.MQConfig;
import com.creditcalculator.models.CreditLimit;
import com.creditcalculator.models.User;
import com.creditcalculator.repository.CreditLimitRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Slf4j
@Service
public class CreditLimitCalculationService {

    private static final int CREDIT_MULTIPLIER_FACTOR = 4;

    private final CreditLimitRepository creditLimitRepository;

    private final CreditScoreService creditScoreService;

    private final UserService userService;

    private final RabbitTemplate rabbitTemplate;

    public CreditLimitCalculationService(CreditLimitRepository creditLimitRepository, CreditScoreService creditScoreService, UserService userService, RabbitTemplate rabbitTemplate) {
        this.creditLimitRepository = creditLimitRepository;
        this.creditScoreService = creditScoreService;
        this.userService = userService;
        this.rabbitTemplate = rabbitTemplate;
    }


    /**
     * Calculates Credit Limit for based on User Income and User Credit Score.
     * If Credit Score is less than 500, credit request will be denied (Credit Limit equals 0).
     *
     * @param income
     * @param creditScore
     * @return calculatedCreditLimit
     */
    double calculateCreditLimit(Double income, Integer creditScore){
        double creditLimit = 0.;
        if (creditScore>= 1000) {
            creditLimit = income * CREDIT_MULTIPLIER_FACTOR;
        }else if (creditScore < 1000 && creditScore > 500 && income> 5000){
            creditLimit = 20000;
        }else if (creditScore < 1000 && creditScore > 500 & income < 5000){
            creditLimit = 10000;
        }
        return creditLimit;
    }

    public boolean requestCreditLimit(String username){
        List<CreditLimit> existingCreditLimits = creditLimitRepository.findAllByUsername(username);
        if (!CollectionUtils.isEmpty(existingCreditLimits)){
            log.info("Denied because existing requests.");
            return false;
        }
        User user = userService.getUserByUsername(username);
        Integer creditScore = creditScoreService.getRandomCreditScore();
        // calculate credit limit
        double calculateCreditLimit = calculateCreditLimit(user.getIncome(), creditScore);
        if (calculateCreditLimit <= 0){
            log.info("Credit request is rejected due to credit score:" + creditScore);
            CreditLimit creditLimit = new CreditLimit(username, creditScore, calculateCreditLimit, false);
            creditLimitRepository.save(creditLimit);
            return false;
        }
        // creates credit limit object
        CreditLimit creditLimit = new CreditLimit(username, creditScore, calculateCreditLimit, true);

        // save credit limit to DB if its approved
        creditLimitRepository.save(creditLimit);
        log.info("Credit request is approved! Credit Limit is :" + calculateCreditLimit +
                "\nIncome is: " + user.getIncome() + "\nCredit score is: " + creditScore);
        // send message to RabbitMQ on approval

        rabbitTemplate.convertAndSend(MQConfig.EXCHANGE, MQConfig.ROUTING_KEY, new CreditApprovedEvent(username,String.valueOf(calculateCreditLimit),
                        user.getFirstNameLastName()));

        return true;
    }

    public List<CreditLimit> getApprovedCreditLimits(String username) {
        return creditLimitRepository.findAllByUsername(username);

    }
}
