package com.intuit.accountprocessor.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.intuit.accountprocessor.dto.AddressRequest;
import com.intuit.accountprocessor.domain.StdAddress;
import com.smartystreets.api.us_street.Candidate;
import com.smartystreets.api.us_street.Components;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AddressStandadization {
    ObjectMapper mapper=new ObjectMapper();
    @Autowired
    UsStreetClient usStreetClient;
    public StdAddress verify(AddressRequest address)  {
        StdAddress stdAddress=null;
        if(address.getCountry().equalsIgnoreCase("USA") || address.getCountry().equalsIgnoreCase("US") ) {
            stdAddress= standardUSAddress(address);
        }else{
            stdAddress=standardOtherAddress(address);
        }
        return stdAddress;
    }

    private StdAddress standardOtherAddress(AddressRequest address)  {
        return null;
    }
    private StdAddress standardUSAddress(AddressRequest address)  {

        StdAddress stdAddress=null;
        try{
            Candidate candidate=usStreetClient.verify(address);
            if(candidate!=null) {

                Components components = candidate.getComponents();
                stdAddress = StdAddress.builder()
                        .addressLine1(candidate.getDeliveryLine1())
                        .addressLine2(candidate.getDeliveryLine2())
                        .streetName(components.getStreetName())
                        .cityName(components.getCityName())
                        .state(components.getState())
                        .country("USA")
                        .zipCode(components.getZipCode())
                        .deliveryPointBarcode(candidate.getDeliveryPointBarcode())
                        .metadata(mapper.writeValueAsString(candidate))
                        .build();
            }

        }catch(Exception e){
            log.error(e.getMessage(),e);
        }
        return stdAddress;

    }
}

