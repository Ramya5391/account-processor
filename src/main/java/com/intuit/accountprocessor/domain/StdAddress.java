package com.intuit.accountprocessor.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="Address")
@Data
@AllArgsConstructor(staticName = "of")
@Builder
public class StdAddress {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="address_id")
    private long addressId;

    @Column(name="address_line1", nullable=true, length=200)
    String addressLine1;

    @Column(name="address_line2", nullable=true, length=200)
    String addressLine2;


    @Column(name="street_name", nullable=false, length=200)
    String streetName;

    @Column(name="city_name", nullable=false, length=50)
    String cityName;

    @Column(name="state", nullable=false, length=50)
    String state;

    @Column(name="country", nullable=false, length=50)
    String country;

    @Column(name="zipcode", nullable=false, length=20)
    String zipCode;

    @Column(name="delivery_point_barcode",nullable=false,unique=true)
    String deliveryPointBarcode;
    @Column(name="metadata", nullable=false,columnDefinition = "TEXT")
    String metadata;

    public StdAddress(){


    }

}
