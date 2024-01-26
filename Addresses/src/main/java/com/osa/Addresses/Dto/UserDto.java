package com.osa.Addresses.Dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class UserDto {
    private Long id;
    private Long billingId;
    private Long shippingId;
    private String userName;
    private String emailAddress;
}
