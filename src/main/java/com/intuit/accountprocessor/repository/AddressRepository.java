package com.intuit.accountprocessor.repository;


import com.intuit.accountprocessor.domain.Account;
import com.intuit.accountprocessor.domain.StdAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AddressRepository extends JpaRepository<StdAddress, Long> {

    List<StdAddress> findById(long userId);

    List<StdAddress> findByDeliveryPointBarcode(String dpbc);
}