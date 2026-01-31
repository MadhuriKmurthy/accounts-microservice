package com.microservices.accounts.dto;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
//@Schema(name = "Error", description = "Data Transfer Object for Error information")
public class ErrorDto {

    //@Schema(description = "HTTP status code of the error")
    private HttpStatus errorCode;

    //@Schema(description = "Detailed error message")
    private String errorMessage;

    //@Schema(description = "API path where the error occurred")
    private String apiPath;

    //@Schema(description = "Timestamp when the error occurred")
    private LocalDateTime errorOccurredTime;

}
