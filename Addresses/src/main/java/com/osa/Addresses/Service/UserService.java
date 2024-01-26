package com.osa.Addresses.Service;

import com.osa.Addresses.Dto.UserDto;
import com.osa.Addresses.Entity.BillingDetails;
import com.osa.Addresses.Entity.Shipping;
import com.osa.Addresses.Entity.User;
import com.osa.Addresses.Exception.BillingDetailsNotFoundException;
import com.osa.Addresses.Exception.ShippingDetailsNotFoundException;
import com.osa.Addresses.Repository.BillingRepository;
import com.osa.Addresses.Repository.ShippingRepository;
import com.osa.Addresses.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BillingRepository billingRepository;

    @Autowired
    private ShippingRepository shippingRepository;


    public User createUser(UserDto userRequest) {
        User newUser = new User();
        newUser.setUserName(userRequest.getUserName());
        newUser.setEmailAddress(userRequest.getEmailAddress());

        // Create Billing if billingId is provided
        if (userRequest.getBillingId() != null) {
            BillingDetails billing = billingRepository.findById(userRequest.getBillingId())
                    .orElseThrow(() -> new BillingDetailsNotFoundException("Billing not found with id: " + userRequest.getBillingId()));
            newUser.setBillingDetails(billing);
        }else{
            throw new BillingDetailsNotFoundException("Billing ID cannot be null");
        }
        if (userRequest.getShippingId() != null) {
            Shipping shipping = shippingRepository.findById(userRequest.getShippingId())
                    .orElseThrow(() -> new ShippingDetailsNotFoundException("Shipping not found with id: " + userRequest.getShippingId()));
            newUser.setShipping(shipping);
        }else{
            throw new ShippingDetailsNotFoundException("Shipping ID cannot be null");

        }

        return userRepository.save(newUser);
    }

}
