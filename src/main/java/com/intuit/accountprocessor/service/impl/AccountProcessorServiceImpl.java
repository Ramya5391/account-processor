package com.intuit.accountprocessor.service.impl;

import com.intuit.accountprocessor.domain.Account;
import com.intuit.accountprocessor.domain.StdAddress;
import com.intuit.accountprocessor.dto.AccountRequest;
import com.intuit.accountprocessor.dto.AddressRequest;
import com.intuit.accountprocessor.repository.AccountProcessorRepository;
import com.intuit.accountprocessor.repository.AddressRepository;
import com.intuit.accountprocessor.service.AccountProcessorService;
import com.intuit.accountprocessor.util.AddressStandadization;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class AccountProcessorServiceImpl implements AccountProcessorService {

    @Autowired
    AddressStandadization addressStandadization;

    @Autowired
    AccountProcessorRepository accountProcessorRepository;

    @Autowired
    AddressRepository addressRepository;
    @Override
    public Account processAndSaveAccount(AccountRequest accountDTO) {
      AddressRequest addressDTO=  accountDTO.getAddress();

        List<Account> accounts= accountProcessorRepository.findByEmailAddress(accountDTO.getEmailAddress());
        if(!accounts.isEmpty()) {
            log.debug("Account with email Address already exists in db");
            return accounts.get(0);
        }
      StdAddress stdAddress=addressStandadization.verify(addressDTO);
      if(stdAddress==null)
      {
          throw new RuntimeException("Invalid Address");
      }
       List<StdAddress> addresses= addressRepository.findByDeliveryPointBarcode(stdAddress.getDeliveryPointBarcode());
    if(!addresses.isEmpty())
    {
        log.debug("Address already exists in db");
        stdAddress=addresses.get(0);
    }
        Account acc=Account.builder().firstName(accountDTO.getFirstName())
                .lastName(accountDTO.getLastName())
                .emailAddress(accountDTO.getEmailAddress())
                .stdAddress(stdAddress).build();
        return accountProcessorRepository.save(acc);
    }

    @Override
    public Account getDetails(long accountId) {

        return accountProcessorRepository.findByAccountId(accountId);
    }
}
