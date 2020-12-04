package com.intuit.accountprocessor.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Response {
    private String errorMessage;
    private Object result;
}
