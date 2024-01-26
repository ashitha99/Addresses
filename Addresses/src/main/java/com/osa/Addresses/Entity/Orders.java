package com.osa.Addresses.Entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;


    @Column(name = "product")
    private String productName;

    @Column(name = "order_Date")
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "shipping_id")
    private Shipping shipping;

}
