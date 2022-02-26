package com.creditcalculator;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CreditApprovedEvent {

    private String username;
    private String creditLimit;
    private String firstNameLastName;

    public CreditApprovedEvent(String username, String creditLimit, String firstNameLastName){
        this.username = username;
        this.creditLimit = creditLimit;
        this.firstNameLastName = firstNameLastName;
    }

}
