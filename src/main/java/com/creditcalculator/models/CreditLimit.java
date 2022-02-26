package com.creditcalculator.models;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Getter
@Entity
public class CreditLimit {

    @Id
    private UUID id;
    private String username;
    private Integer creditScore;
    private Double creditLimit;
    private Boolean creditApproved;

    public CreditLimit(String username, Integer creditScore, Double creditLimit, Boolean creditApproved) {
        this.creditApproved = creditApproved;
        this.id = UUID.randomUUID();
        this.username = username;
        this.creditScore = creditScore;
        this.creditLimit = creditLimit;
    }

    public CreditLimit() {
    }
}
