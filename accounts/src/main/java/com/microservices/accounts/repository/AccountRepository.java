package com.microservices.accounts.repository;

import com.microservices.accounts.entity.Account;
import com.microservices.accounts.entity.Customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import jakarta.transaction.Transactional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

  Optional<Account> findByCustomerId(Long customerId);

  @Transactional
  @Modifying
  void deleteByCustomerId(Long customerId);
}
