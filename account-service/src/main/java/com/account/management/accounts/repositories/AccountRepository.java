package com.account.management.accounts.repositories;

import com.account.management.accounts.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByCustomerId(String customerId);

    Optional<Account> findByIdAndCustomerId(Long accountId, String customerId);
}
