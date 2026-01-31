package com.microservices.accounts.service;

import com.microservices.accounts.dto.AccountDto;
import com.microservices.accounts.dto.CustomerDto;
import com.microservices.accounts.entity.Account;
import com.microservices.accounts.entity.Customer;
import com.microservices.accounts.exception.CustomerAlreadyExistsException;
import com.microservices.accounts.exception.ResourceNotFoundException;
import com.microservices.accounts.mapper.AccountMapper;
import com.microservices.accounts.mapper.CustomerMapper;
import com.microservices.accounts.repository.AccountRepository;
import com.microservices.accounts.repository.CustomerRepository;
import com.microservices.accounts.util.Constants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements IAccountService{

  @Autowired
  private final AccountRepository accountRepository;

  @Autowired
  private final CustomerRepository customerRepository;

  @Override
  public void createAccount(final CustomerDto customerDto) {

    final Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());
    Optional<Customer> customerExists = customerRepository.findByMobileNumber(customer.getMobileNumber());
    if(customerExists.isPresent()){
      throw new CustomerAlreadyExistsException("Customer with mobile number already exists");
    }
    customer.setCreatedAt(LocalDateTime.now());
    customer.setCreatedBy(Constants.SYSTEM_USER);
    Customer savedCustomer = customerRepository.save(customer);
    // Further logic to create account can be added here
    Account savedAccount = accountRepository.save(createAccount(savedCustomer, Constants.SAVINGS_ACCOUNT));


  }


  private Account createAccount(Customer customer, String accountType){
    final Account account = new Account();
    account.setCustomerId(customer.getCustomerId());
    long randomAccountNumber = new Random().nextLong();
    account.setAccountNumber(randomAccountNumber);
    account.setAccountType(accountType);
    account.setBranchAddress("Default Branch Address");
    account.setCreatedAt(LocalDateTime.now());
    account.setCreatedBy(Constants.SYSTEM_USER);
    return account;
  }

  @Override
  public CustomerDto fetchAccountAndCustomerInfo(String mobileNumber) {
    final Optional<Customer> customerOpt = Optional.ofNullable(customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
            () -> new ResourceNotFoundException("Customer", "mobile number", mobileNumber)
    ));
    final Optional<Account> accountOpt = Optional.ofNullable(accountRepository.findByCustomerId(customerOpt.get().getCustomerId()).orElseThrow(
            () -> new ResourceNotFoundException("Account", "customer id", String.valueOf(customerOpt.get().getCustomerId()))
    ));

    final CustomerDto customer = CustomerMapper.mapToCustomerDto(customerOpt.get(), new CustomerDto());
    customer.setAccount(AccountMapper.mapToAccountDto(accountOpt.get(), new AccountDto()));

    return customer;
  }

  @Override
  public boolean updateCustomerDetails(CustomerDto updatedCustomerInfo) {
    boolean isUpdated = false;
    AccountDto accountDto = updatedCustomerInfo.getAccount();
    if (accountDto != null) {
      Account account = accountRepository.findById(accountDto.getAccountNumber()).orElseThrow(
              () -> new ResourceNotFoundException("Account", "account id", String.valueOf(accountDto.getAccountNumber()))
      );

      Long customerId = account.getCustomerId();
      Customer existingCustomer = customerRepository.findById(customerId).orElseThrow(
              () -> new ResourceNotFoundException("Customer", "customer id", String.valueOf(customerId))
      );

      CustomerMapper.mapToCustomer(updatedCustomerInfo, existingCustomer);
      customerRepository.save(existingCustomer);
      isUpdated = true;
    }

    return isUpdated;
  }

  @Override
  public boolean deleteAccountAndCustomer(final String mobileNumber) {
    final Optional<Customer> customerOpt = Optional.ofNullable(customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
            () -> new ResourceNotFoundException("Customer", "mobile number", mobileNumber)
    ));
    final Optional<Account> accountOpt = Optional.ofNullable(accountRepository.findByCustomerId(customerOpt.get().getCustomerId()).orElseThrow(
            () -> new ResourceNotFoundException("Account", "customer id", String.valueOf(customerOpt.get().getCustomerId()))
    ));
    accountRepository.deleteById(accountOpt.get().getAccountNumber());
    customerRepository.deleteById(customerOpt.get().getCustomerId());
    return true;
  }
}
