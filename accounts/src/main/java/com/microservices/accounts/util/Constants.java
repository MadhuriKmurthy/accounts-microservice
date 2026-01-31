package com.microservices.accounts.util;

public class Constants {

  //private Constructor
  private Constants(){
  }
  // Success
  public static final String ACCOUNT_CREATION_SUCCESS = "Account has been created successfully.";
  public static final String SUCCESS_CODE = "201";

  // Failure
  public static final String ACCOUNT_CREATION_FAILURE = "Account creation failed. Please try again later.";
  public static final String INTERNAL_SERVER_ERROR = "500";


  public static final String SAVINGS_ACCOUNT = "SAVINGS_ACCOUNT";
  public static final String CURRENT_ACCOUNT = "CURRENT_ACCOUNT";

  public static final String SYSTEM_USER = "SYSTEM_USER";

  public static final String CUSTOMER_UPDATE_SUCCESS = "Customer details updated successfully.";
  public static final String CUSTOMER_UPDATE_FAILURE = "Failed to update customer details.";

}
