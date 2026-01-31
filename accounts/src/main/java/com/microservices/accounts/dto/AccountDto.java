package com.microservices.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Schema(name = "Account", description = "Data Transfer Object for Account information")
public class AccountDto {

  @Schema(description = "Account number of the customer")
  @NotEmpty(message = "Account number cannot be empty")
  @NotNull(message = "Account number cannot be null")
  @Pattern(regexp = "^[0-9]{10,18}$", message = "Account number must be a valid number between 10 to 18 digits")
  private Long accountNumber;

  @Schema(description = "Type of the account", example = "SAVINGS")
  @NotEmpty(message = "Customer ID cannot be empty")
  @NotNull(message = "Customer ID cannot be null")
  private String accountType;

  @Schema(description = "Address of the branch where the account is held", example = "123 Main St, Cityville")
  @NotEmpty(message = "Branch Address cannot be empty")
  @NotNull(message = "Branch Address cannot be null")
  private String branchAddress;
}
