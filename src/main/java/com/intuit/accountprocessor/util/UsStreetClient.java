package com.intuit.accountprocessor.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.intuit.accountprocessor.dto.AddressRequest;
import com.smartystreets.api.ClientBuilder;
import com.smartystreets.api.exceptions.SmartyException;
import com.smartystreets.api.us_street.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class UsStreetClient {

    @Value("${smartystreets.authId}")
    String authId;
    @Value("${smartystreets.authToken}")
    String authToken;
    public Candidate  verify(AddressRequest address) throws IOException, SmartyException {

        System.out.println("Step 0. Wire up the client with your keypair.");
        Client client = new ClientBuilder(authId, authToken).buildUsStreetApiClient();

        System.out.println("Step 1. Make a lookup. (BTW, you can also send entire batches of lookups...)");
        Lookup lookup = new Lookup();
        //lookup.setStreet("10326 S Kays Chapel Rd");
       // lookup.setLastline("Fredericksburg");
       // lookup.setState("IN");
        lookup.setStreet(address.getStreet());
        lookup.setState(address.getProvinceState());
        lookup.setCity(address.getCity());
        lookup.setMaxCandidates(10);

        System.out.println("Step 2. Send the lookup.");
        client.send(lookup);
        ObjectMapper mapper=new ObjectMapper();

        System.out.println("Step 3. Show the resulting candidate addresses:");
        int index = 0;
        for (Candidate candidate : lookup.getResult()) {
            System.out.printf("- %d: %s, %s\n", index, candidate.getDeliveryLine1(), candidate.getLastLine());
            System.out.println(mapper.writeValueAsString(candidate));

            index++;
        }
        return lookup.getResult(0);
    }

}