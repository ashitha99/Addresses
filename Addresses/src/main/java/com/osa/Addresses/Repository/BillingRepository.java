package com.osa.Addresses.Repository;

import com.osa.Addresses.Entity.BillingDetails;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BillingRepository extends JpaRepository<BillingDetails,Long> {

}
