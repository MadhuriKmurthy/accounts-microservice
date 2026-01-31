package com.microservices.accounts.service;

import com.microservices.accounts.dto.CustomerDto;
import com.microservices.accounts.entity.Customer;

public interface IAccountService {

  /**
   * Create account for customer
   * @param customer CustomerDto object containing customer and account details
   */
  void createAccount(final CustomerDto customer);

  /**
   * Fetch account and customer details by mobile number
   * @param mobileNumber String  customer's mobile number
   * @return CustomerDto  object containing customer and account details
   */
  CustomerDto fetchAccountAndCustomerInfo(final String mobileNumber);

  /**
   * Update customer details
   * @param customer  Customer object containing updated customer details
   * @return boolean indicating success or failure
   */
  boolean updateCustomerDetails(final CustomerDto customer);

  boolean deleteAccountAndCustomer(final String mobileNumber);
}
