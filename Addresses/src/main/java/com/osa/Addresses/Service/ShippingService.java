package com.osa.Addresses.Service;

import com.osa.Addresses.Dto.ShippingDto;
import com.osa.Addresses.Entity.Shipping;
import com.osa.Addresses.Exception.AddressNotFoundException;
import com.osa.Addresses.Exception.AddressValidationException;
import com.osa.Addresses.Repository.ShippingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class ShippingService {
    @Autowired
    private ShippingRepository shippingRepository;


    public Shipping saveDetails(ShippingDto shippingDto) {

        //During the checkout process, the system should prompt users to enter shipping information.
        //If the entered address is incomplete or contains errors, the system should respond with appropriate error messages guiding the user on necessary corrections.


        if(shippingDto.getRecipientName()==null||shippingDto.getRecipientName().isEmpty()) {
            throw new AddressValidationException("Please enter the Recipient's name");
        }
        if(shippingDto.getStreetAddress()==null||shippingDto.getStreetAddress().isEmpty()) {
            throw new AddressValidationException("Please enter the street address");
        }
        if(shippingDto.getCity()==null||shippingDto.getCity().isEmpty()){
            throw new AddressValidationException("please enter the city name");
        }
        if(shippingDto.getState()==null||shippingDto.getState().isEmpty()){
            throw new AddressValidationException("please enter the state name");
        }
        if(shippingDto.getCountry()==null||shippingDto.getCountry().isEmpty()){
            throw new AddressValidationException("Enter the country name");
        }
        if(shippingDto.getPhoneNo()==null||shippingDto.getCountry().isEmpty()){
            throw new AddressValidationException("Enter the Phone Number");
        }
        // If all validation checks pass, create a new ShippingDetails object
        Shipping shippingDetails=new Shipping();
        shippingDetails.setRecipientName(shippingDto.getRecipientName());
        shippingDetails.setStreetAddress(shippingDto.getStreetAddress());
        shippingDetails.setCity(shippingDto.getCity());
        shippingDetails.setState(shippingDto.getState());
        shippingDetails.setPostalCode(shippingDto.getPostalCode());
        shippingDetails.setCountry(shippingDto.getCountry());
        shippingDetails.setPhoneNo(String.valueOf(shippingDto.getPhoneNo()));

        return shippingRepository.save(shippingDetails);
    }

    public Optional<Shipping> findAddressById(Long id) {
        return shippingRepository.findById(id);
    }

    public List<Shipping> getAllAddresses() {
        return shippingRepository.findAll();
    }

    public void updateProfile(ShippingDto shippingDto) {
        Optional<Shipping> details = shippingRepository.findById(shippingDto.getId());
        if (details.isPresent()) {
            Shipping existingAddress = details.get();
            existingAddress.setStreetAddress(shippingDto.getStreetAddress());
            existingAddress.setCity(shippingDto.getCity());
            existingAddress.setState(shippingDto.getState());
            existingAddress.setCountry(shippingDto.getCountry());
            existingAddress.setPostalCode(shippingDto.getPostalCode());
            existingAddress.setPhoneNo(String.valueOf(shippingDto.getPhoneNo()));

            shippingRepository.save(existingAddress);
        } else {
            throw new AddressNotFoundException("AddressNotFound");
        }
    }

    public void deleteById(Long id) {
        shippingRepository.deleteById(id);
    }

  /*  public List<Shipping> getShippingDetailsByBillingId(Long billingId) {
        return shippingRepository.findByBillingId(billingId);
    }*/

    //Once the address is validated, the system securely stores this information.
    // The validated addresses are then associated with the specific order, ensuring that the correct shipping details are linked to each order for accurate and successful delivery.
  /*  public Lis t<Shipping> getShippingDetailsByOrderId(Long orderId) {
        return shippingRepository.findByOrderId(orderId);
    }*/


}
