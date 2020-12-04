package com.intuit.accountprocessor.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AddressRequest {
    @NotNull
    private String street;
    @NotNull
    private String city;
    @JsonProperty("province_state")
    @NotNull
    private String provinceState;
    @NotNull
    private String country;
}
