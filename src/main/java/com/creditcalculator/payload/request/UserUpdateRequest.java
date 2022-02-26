package com.creditcalculator.payload.request;

import lombok.AllArgsConstructor;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
public class UserUpdateRequest {
    @NotBlank
    private String username;
    @NotBlank
    private String firstNameLastName;
    @NotBlank
    private String phoneNumber;
    private Double income;

    public String getUsername() {
        return username;
    }

    public String getFirstNameLastName() {
        return firstNameLastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Double getIncome() {
        return income;
    }

}
