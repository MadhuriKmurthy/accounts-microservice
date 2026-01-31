package com.microservices.accounts.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Customer not found")
public class ResourceNotFoundException extends RuntimeException{

  public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
    super(String.format("%s not found with %s : '%s'", resourceName, fieldName, fieldValue));
  }
}
