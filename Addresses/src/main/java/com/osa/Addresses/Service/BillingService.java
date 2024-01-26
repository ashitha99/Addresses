package com.osa.Addresses.Service;

import com.osa.Addresses.Controller.BillingController;
import com.osa.Addresses.Dto.BillingDto;
import com.osa.Addresses.Entity.BillingDetails;
import com.osa.Addresses.Exception.AddressNotFoundException;
import com.osa.Addresses.Exception.AddressValidationException;
import com.osa.Addresses.Repository.BillingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BillingService {

    private static final Logger logger = LoggerFactory.getLogger(BillingController.class);
    @Autowired
    private BillingRepository billingRepository;


    public BillingDetails saveDetails(BillingDto billingDto) {

        //User enters complete and accurate shipping and billing information.
        //If User enters an address with missing details (e.g., no street name).
        //The system should detect the incomplete address, respond with an appropriate error message, and guide the user to provide the missing information.
        if (billingDto.getStreetAddress() == null || billingDto.getStreetAddress().isEmpty()) {
            throw new AddressValidationException("Street Address cannot be empty");
        }
        if (billingDto.getCity() == null || billingDto.getCity().isEmpty()) {
            throw new AddressValidationException("City cannot be empty");
        }
        if (billingDto.getState() == null || billingDto.getState().isEmpty()) {
            throw new AddressValidationException("State cannot be empty");
        }
        if (billingDto.getCountry() == null || billingDto.getCountry().isEmpty()) {
            throw new AddressValidationException("Country cannot be empty");
        }
        if (billingDto.getPaymentType() == null || billingDto.getPaymentType().isEmpty()) {
            throw new AddressValidationException("It cannot be empty");
        }
        if (billingDto.getCardHolderName() == null || billingDto.getCardHolderName().isEmpty()) {
            throw new AddressValidationException("Please Enter CardHolder Name");
        }
        if (billingDto.getCreditCardNumber() == null || billingDto.getCreditCardNumber().isEmpty()) {
            throw new AddressValidationException("Please enter cc number");
        }

        if (billingDto.getExpirationDate() == null) {
            throw new AddressValidationException("Please fill the Expiration Date");
        }

        if (billingDto.getEmail() == null || billingDto.getEmail().isEmpty()) {
            throw new AddressValidationException("Email address cannot be empty");
        }
        if (billingDto.getPhoneNo() == null) {
            throw new AddressValidationException("Phone number cannot be empty");
        }

        BillingDetails details = new BillingDetails();
        details.setName(billingDto.getName());
        details.setStreetAddress(billingDto.getStreetAddress());
        details.setCity(billingDto.getCity());
        details.setState(billingDto.getState());
        details.setPostalCode(billingDto.getPostalCode());
        details.setCountry(billingDto.getCountry());
        details.setPaymentType(billingDto.getPaymentType());
        details.setCardHolderName(billingDto.getCardHolderName());
        details.setCreditCardNumber(billingDto.getCreditCardNumber());
        details.setExpirationDate(billingDto.getExpirationDate());
        details.setEmail(billingDto.getEmail());
        details.setPhoneNumber(billingDto.getPhoneNo());

        // Mask the credit card number before saving
        String maskedCreditCardNumber = maskCreditCardNumber(billingDto.getCreditCardNumber());

        details.setCreditCardNumber(maskedCreditCardNumber);
        return billingRepository.save(details);
    }

    private String maskCreditCardNumber(String creditCardNumber) {
        int visibleChars = 4; // Number of visible characters at the end
        int totalChars = creditCardNumber.length();

        // Calculate the number of characters to mask
        int maskedChars = totalChars - visibleChars;

        // Create the masked credit card number
        StringBuilder maskedNumber = new StringBuilder();
        for (int i = 0; i < maskedChars; i++) {
            maskedNumber.append('*');
        }
        maskedNumber.append(creditCardNumber.substring(maskedChars));

        return maskedNumber.toString();
    }

    public Optional<BillingDetails> findAddressById(Long id) {
        return billingRepository.findById(id);
    }

    public List<BillingDetails> getAllAddresses() {
        return billingRepository.findAll();
    }

    public void updateProfile(BillingDto billingDto) {
        //// Check if the BillingDetails with the given ID exists
        Optional<BillingDetails>details=billingRepository.findById(billingDto.getId());
        if(details.isPresent()){
            // If the BillingDetails exists, update its attributes with the values from the BillingDto
            BillingDetails existingAddress=details.get();
            existingAddress.setName(billingDto.getName());
            existingAddress.setStreetAddress(billingDto.getStreetAddress());
            existingAddress.setCity(billingDto.getCity());
            existingAddress.setState(billingDto.getState());
            existingAddress.setCountry(billingDto.getCountry());
            existingAddress.setPostalCode(billingDto.getPostalCode());
            existingAddress.setPaymentType(billingDto.getPaymentType());
            existingAddress.setCardHolderName(billingDto.getCardHolderName());
            existingAddress.setCreditCardNumber(billingDto.getCreditCardNumber());
            existingAddress.setExpirationDate(billingDto.getExpirationDate());

            // Save the updated BillingDetails to the repository
            billingRepository.save(existingAddress);
        } else {
            //If the BillingDetails with the given ID is not found, throw an exception
            throw new AddressNotFoundException("AddressNotFound");
        }


    }

    public void deleteById(Long id) {
        billingRepository.deleteById(id);


    }



}
