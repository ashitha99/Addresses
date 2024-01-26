package com.osa.Addresses.Controller;

import com.osa.Addresses.Dto.ShippingDto;
import com.osa.Addresses.Entity.Shipping;
import com.osa.Addresses.Exception.AddressValidationException;
import com.osa.Addresses.Service.ShippingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/addressMs")
public class ShippingController {

    private static final Logger logger = LoggerFactory.getLogger(ShippingController.class);
    @Autowired
    private ShippingService shippingService;


    @PostMapping("/shipping")
    public ResponseEntity<Shipping> createShipping(@RequestBody ShippingDto shippingDto) {
        logger.info("Creating shipping with request: {}", shippingDto);
        Shipping createdShipping = shippingService.saveDetails(shippingDto);
        return new ResponseEntity<>(createdShipping, HttpStatus.CREATED);
    }

    @GetMapping("/shippingAddresses/{id}")
    public Optional<Shipping> getAddressById(@PathVariable Long id) {
        logger.info("Fetching shipping details for ID: {}", id);
        return shippingService.findAddressById(id);
    }

    @GetMapping("/shippingAddresses")
    public List<Shipping> getAllAddresses() {
        return shippingService.getAllAddresses();
    }

    @PutMapping("/updateShippingDetails")
    public ResponseEntity<?> updateAddress(@RequestBody ShippingDto shippingDto) {
            System.out.println("Updating the Details" + shippingDto);
            shippingService.updateProfile(shippingDto);
            return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping("/deleteShippingDetails/{id}")
    public void deleteBillingDetail(@PathVariable Long id) {
        shippingService.deleteById(id);
    }

   /* @GetMapping("/shippingAddressesByBillingId/{billingId}")
    public List<Shipping> getShippingDetailsByBillingId(@PathVariable Long billingId) {
        return shippingService.getShippingDetailsByBillingId(billingId);
    }*/

    /*@GetMapping("/shippingAddressByOrderId/{orderId}")
    public List<Shipping> getShippingDetailsByOrderId(@PathVariable Long orderId) {
        return shippingService.getShippingDetailsByOrderId(orderId);
    }*/

}
