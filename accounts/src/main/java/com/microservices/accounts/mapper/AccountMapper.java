package com.microservices.accounts.mapper;

import com.microservices.accounts.dto.AccountDto;
import com.microservices.accounts.entity.Account;

public class AccountMapper {

  public static AccountDto mapToAccountDto(Account account, AccountDto accountDto) {
    accountDto.setAccountNumber(account.getAccountNumber());
    accountDto.setAccountType(account.getAccountType());
    accountDto.setBranchAddress(account.getBranchAddress());
    return accountDto;
  }

  public static Account mapToAccountEntity(AccountDto accountDto, Account account) {
    account.setAccountNumber(accountDto.getAccountNumber());
    account.setAccountType(accountDto.getAccountType());
    account.setBranchAddress(accountDto.getBranchAddress());
    return account;
  }
}
