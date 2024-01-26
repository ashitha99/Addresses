package com.osa.Addresses.Repository;

import com.osa.Addresses.Entity.Shipping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShippingRepository extends JpaRepository<Shipping,Long> {

}
