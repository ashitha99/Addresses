package com.osa.Addresses.Controller;

import com.osa.Addresses.Dto.BillingDto;
import com.osa.Addresses.Entity.BillingDetails;
import com.osa.Addresses.Exception.AddressValidationException;
import com.osa.Addresses.Service.BillingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/addressMs")
public class BillingController {

    private static final Logger logger = LoggerFactory.getLogger(BillingController.class);

    @Autowired
    private BillingService billingService;

    @PostMapping("/billing")
    public ResponseEntity<BillingDetails> createDetails(@RequestBody BillingDto billingDto) {
            // Call the service to save billing details
            BillingDetails result = billingService.saveDetails(billingDto);
            logger.info("Billing Details Creatred...");
            return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping("/billingAddresses/{id}")
    public Optional<BillingDetails> getAddressById(@PathVariable Long id) {
        logger.info("Fetching billing details for ID: {}", id);
        return billingService.findAddressById(id);
    }

    @GetMapping("/billingAddresses")
    public List<BillingDetails> getAllAddresses() {
        return billingService.getAllAddresses();
    }

    @PutMapping("/updateDetails")
    public ResponseEntity<?> updateAddress(@RequestBody BillingDto billingDto) {
        logger.info("Updating the Details: {}", billingDto);
        billingService.updateProfile(billingDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/deleteBillingDetails/{id}")
    public void deleteBillingDetail(@PathVariable Long id) {
        billingService.deleteById(id);
        logger.info("Billing details deleted successfully");

    }
}


