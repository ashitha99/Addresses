package com.osa.Addresses.Dto;

import com.osa.Addresses.Entity.Shipping;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
public class OrderRequest {
    private Long id;
    private Long shippingId;
    private String productName;
    private LocalDate date;


}
