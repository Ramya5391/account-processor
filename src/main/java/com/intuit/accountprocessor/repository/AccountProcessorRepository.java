package com.intuit.accountprocessor.repository;


import com.intuit.accountprocessor.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AccountProcessorRepository extends JpaRepository<Account, Long> {

    Account findByAccountId(long accountId);

    List<Account> findByEmailAddress(String emailAddress);
}