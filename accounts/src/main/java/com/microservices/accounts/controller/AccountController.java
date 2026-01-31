package com.microservices.accounts.controller;

import com.microservices.accounts.dto.AccountControlInfoDto;
import com.microservices.accounts.dto.CustomerDto;
import com.microservices.accounts.dto.ErrorDto;
import com.microservices.accounts.dto.ResponseDto;
import com.microservices.accounts.service.IAccountService;
import com.microservices.accounts.util.Constants;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.micrometer.observation.autoconfigure.ObservationProperties;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;

@Tag(
        name = "Account Controller",
        description = "CRUD APIs for managing customer accounts"
)
@RestController
@RequestMapping(path = "/api/account", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
public class AccountController {

  @Autowired
  private IAccountService accountService;

  @Value("${build.version}")
  private String buildVersion;

  @Autowired
  private Environment environment;

  @Autowired
  private AccountControlInfoDto accountControlInfoDto;

  @Operation(
          summary = "Create Account REST API",
          description = "Creates a new customer account with the provided details"
  )
  @ApiResponse(
          responseCode = "201",
          description = "Account created successfully"
  )
  @PostMapping("/create")
  public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody CustomerDto customer){

    accountService.createAccount(customer);

    final ResponseDto response = new ResponseDto(Constants.SUCCESS_CODE,
            Constants.ACCOUNT_CREATION_SUCCESS);

    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @Operation(
          summary = "Fetch Customer Details REST API",
          description = "Fetches customer and account details based on the provided mobile number"
  )
  @ApiResponse(
          responseCode = "200",
          description = "Customer details fetched successfully"
  )
  @GetMapping("/fetch")
  public ResponseEntity<CustomerDto> fetchCustomerDetails(@RequestParam
                                                            @Pattern(message = "Mobile number must be a valid 10-digit number", regexp = "^[0-9]{10}$")
                                                                    String mobileNumber){
    final CustomerDto customerDto = accountService.fetchAccountAndCustomerInfo(mobileNumber);
    return ResponseEntity.status(HttpStatus.OK).body(customerDto);
  }

  @Operation(
          summary = "Update Customer Details REST API",
          description = "Updates the details of an existing customer"
  )
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Customer details updated successfully"),
          @ApiResponse(
                  responseCode = "500",
                  description = "Failed to delete account and customer",
                  content = @Content(
                          schema = @Schema(implementation = ErrorDto.class)
                  )
          )
  })
  @PutMapping("/update")
  public ResponseEntity<ResponseDto> updateCustomerDetails(@Valid @RequestBody CustomerDto customer){
    final boolean isUpdated = accountService.updateCustomerDetails(customer);
    final ResponseDto response;
    if(isUpdated){
      response = new ResponseDto(Constants.SUCCESS_CODE, Constants.CUSTOMER_UPDATE_SUCCESS);
      return ResponseEntity.status(HttpStatus.OK).body(response);
    } else {
      response = new ResponseDto(Constants.INTERNAL_SERVER_ERROR, Constants.CUSTOMER_UPDATE_FAILURE);
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
  }


  @Operation(
          summary = "Delete Account and Customer REST API",
          description = "Deletes a customer and their associated account based on the provided mobile number"
  )
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Customer and Account deleted successfully"),
          @ApiResponse(
                  responseCode = "500",
                  description = "Failed to delete account and customer",
                  content = @Content(
                          schema = @Schema(implementation = ErrorDto.class)
                  )
          )
  })
  @DeleteMapping("/delete")
  public ResponseEntity<ResponseDto> deleteAccountAndCustomer(@RequestParam
                                                                @Pattern(message = "Mobile number must be a valid 10-digit number", regexp = "^[0-9]{10}$")
                                                                        String mobileNumber){
    final boolean isDeleted = accountService.deleteAccountAndCustomer(mobileNumber);
    final ResponseDto response;
    if(isDeleted){
      response = new ResponseDto(Constants.SUCCESS_CODE, "Customer and Account deleted successfully.");
      return ResponseEntity.status(HttpStatus.OK).body(response);
    } else {
      response = new ResponseDto(Constants.INTERNAL_SERVER_ERROR, "Failed to delete account and customer.");
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
  }

  @Operation(
          summary = "Fetch Build Version REST API",
          description = "Fetches current build version of the application"
  )
  @ApiResponse(
          responseCode = "200",
          description = "Build Version returned successfully"
  )
  @GetMapping("/buildVersion")
  public ResponseEntity<String> getBuildInfo(){
    return ResponseEntity.status(HttpStatus.OK).body(buildVersion);
  }



  @Operation(
          summary = "Fetch Java Version REST API",
          description = "Fetches java version using environment variable"
  )
  @ApiResponse(
          responseCode = "200",
          description = "Java Version returned successfully"
  )
  @GetMapping("/java-version")
  public ResponseEntity<String> getJavaVersion(){
    return ResponseEntity.status(HttpStatus.OK).body(environment.getProperty("JAVA_HOME"));
  }


  @Operation(
          summary = "Fetch Contact Info REST API",
          description = "Fetches contact info version using ConfigurationProperties"
  )
  @ApiResponse(
          responseCode = "200",
          description = "Contact Info Details returned successfully"
  )
  @GetMapping("/contact")
  public ResponseEntity<AccountControlInfoDto> getContactInfo(){
    return ResponseEntity.status(HttpStatus.OK).body(accountControlInfoDto);
  }
}
