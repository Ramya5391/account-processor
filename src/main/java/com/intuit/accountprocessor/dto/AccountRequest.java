package com.intuit.accountprocessor.dto;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
public class AccountRequest {

    @NotNull
    private String firstName;
    private String lastName;
    @NotNull
    @Email
    private String emailAddress;
    @Valid
    @NotNull
    private AddressRequest address;
}
