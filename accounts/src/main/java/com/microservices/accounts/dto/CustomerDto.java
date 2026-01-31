package com.microservices.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(name = "Customer", description = "Data Transfer Object for Customer information")
public class CustomerDto {

  @Schema(description = "Name of the customer", example = "Jane Doe")
  @NotEmpty(message = "Name cannot be empty")
  @NotNull(message = "Name cannot be null")
  @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
  private String name;

  @Schema(description = "Email ID of the customer", example = "example@ex.com")
  @NotEmpty(message = "Email cannot be empty")
  @NotNull(message = "Email cannot be null")
  @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$", message = "Email must be a valid email address")
  private String email;

  @Schema(description = "Mobile number of the customer", example = "1234567890")
  @NotEmpty(message = "Mobile number cannot be empty")
  @NotNull(message = "Mobile number cannot be null")
  @Pattern(regexp = "^[0-9]{10}$", message = "Mobile number must be a valid 10-digit number")
  private String mobileNumber;

  @Schema(description = "Account details associated with the customer")
  private AccountDto account;
}
