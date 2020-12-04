package com.intuit.accountprocessor.service;

import com.intuit.accountprocessor.domain.Account;
import com.intuit.accountprocessor.dto.AccountRequest;
import org.springframework.stereotype.Service;

public interface AccountProcessorService {

    public Account processAndSaveAccount(AccountRequest accountDTO);

    public Account getDetails(long accountId);
}
