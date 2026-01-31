package com.microservices.accounts.exception;

import com.microservices.accounts.dto.ErrorDto;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
/**
 * @ControllerAdvice in Spring is a powerful annotation used to define global configurations for
 * controllers. Think of it as a way to centralize and reuse logic that applies across multiple
 * controllers, instead of duplicating it in each one.
 * Main Uses of @ControllerAdvice
 * - Global Exception Handling
 * - Global Data Binding
 * - Global Model Attributes
 */
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
          MethodArgumentNotValidException ex,
          HttpHeaders headers,
          HttpStatusCode status,
          WebRequest request) {
    Map<String,String> validationErrors = new HashMap<>();

    ex.getBindingResult().getAllErrors().forEach(error -> {
      String fieldName = ((FieldError) error).getField();
      String validationMessage = error.getDefaultMessage();
      validationErrors.put(fieldName, validationMessage);
    });

    return new ResponseEntity<>(validationErrors, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorDto> handleGlobalException(Exception ex, WebRequest webRequest) {
    ErrorDto errorDetailsDto = new ErrorDto(
            HttpStatus.INTERNAL_SERVER_ERROR,
            ex.getMessage(),
            webRequest.getDescription(false),
            java.time.LocalDateTime.now()
    );
    return new ResponseEntity<>(errorDetailsDto, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(CustomerAlreadyExistsException.class)
  public ResponseEntity<ErrorDto> handleCustomerAlreadyExistsException(CustomerAlreadyExistsException ex, WebRequest webRequest) {
    ErrorDto errorDetailsDto = new ErrorDto(
            HttpStatus.BAD_REQUEST,
            ex.getMessage(),
            webRequest.getDescription(false),
            java.time.LocalDateTime.now()
    );
    return new ResponseEntity<>(errorDetailsDto, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ErrorDto> handleResourceDoesNotExistsException(ResourceNotFoundException ex, WebRequest webRequest) {
    ErrorDto errorDetailsDto = new ErrorDto(
            HttpStatus.NOT_FOUND,
            ex.getMessage(),
            webRequest.getDescription(false),
            java.time.LocalDateTime.now()
    );
    return new ResponseEntity<>(errorDetailsDto, HttpStatus.NOT_FOUND);
  }
}
