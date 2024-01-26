package com.osa.Addresses.Dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class BillingDto {
    private Long id;
    private Long orderId;
    private String name;
    private String streetAddress;
    private String city;
    private String state;
    private String postalCode;
    private String country;
    private String email;
    private String phoneNo;
    private String paymentType;
    private String cardHolderName;
    private String creditCardNumber;
    private LocalDate expirationDate;

}
