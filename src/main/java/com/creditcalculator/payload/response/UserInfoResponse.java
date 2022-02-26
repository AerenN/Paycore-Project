package com.creditcalculator.payload.response;

import java.util.List;

public class UserInfoResponse {
    private Long id;
    private String username;
    private String firstNameLastName;
    private String phoneNumber;
    private Double income;
    private List<String> roles;

    public UserInfoResponse(Long id, String username, String lastName, String phoneNumber, Double income,
                            List<String> roles) {
        this.id = id;
        this.username = username;
        this.firstNameLastName = lastName;
        this.phoneNumber = phoneNumber;
        this.income = income;
        this.roles = roles;
    }

    public String getFirstNameLastName() {
        return firstNameLastName;
    }

    public void setFirstNameLastName(String firstNameLastName) {
        this.firstNameLastName = firstNameLastName;
    }

    public Double getIncome() {
        return income;
    }

    public void setIncome(Double income) {
        this.income = income;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getRoles() {
        return roles;
    }
}