package com.osa.Addresses.Service;


import com.osa.Addresses.Dto.BillingDto;
import com.osa.Addresses.Entity.BillingDetails;
import com.osa.Addresses.Repository.BillingRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class BillingServiceTest {
    @InjectMocks
    private BillingService billingService;

    @Mock
    private BillingRepository billingRepository;

    @Mock
    private BillingDto billingDto;


    @Test
    public void testSaveDetails_Success() {
        Long id = 1L;
        Long customerId = 1L;
        Long billingId = 1L;
        String recipientName = "ash";
        String streetAddress = "A9-sri sai gruha APTS";
        String city = "bangalore";
        String state = "karnatka";
        String postalCode = "5606032";
        String country = "India";
        String email = "ash@gmil.com";
        Long phoneNo = 9844558347L;
        String paymentType = "creditCard";
        String cardHolderName = "Ashi";
        String creditCardNumber = "45678901344534";
        String expirationDate = "2023-04-02";

        // Mock the necessary methods in shippingDto
        when(billingDto.getStreetAddress()).thenReturn(streetAddress);
        when(billingDto.getCity()).thenReturn(city);
        when(billingDto.getState()).thenReturn(state);
        when(billingDto.getPostalCode()).thenReturn(postalCode);
        when(billingDto.getCountry()).thenReturn(country);
        when(billingDto.getPhoneNo()).thenReturn(String.valueOf(phoneNo));
        when(billingDto.getEmail()).thenReturn(email);
        when(billingDto.getPaymentType()).thenReturn(paymentType);
        when(billingDto.getCardHolderName()).thenReturn(cardHolderName);
        when(billingDto.getCreditCardNumber()).thenReturn(creditCardNumber);
        when(billingDto.getExpirationDate()).thenReturn(LocalDate.parse(expirationDate));



        BillingDetails billing = new BillingDetails();

        when(billingRepository.save(any(BillingDetails.class))).thenReturn(new BillingDetails());

        // Act
        BillingDetails saveDetails = billingService.saveDetails(billingDto);

        // Assert
        assertNotNull(saveDetails);

        // Verify that the repository's save method is called with the expected Shipping object
        verify(billingRepository, times(1)).save(any(BillingDetails.class));
    }
    @Test
    void testFindAddressByIdWhenExists() {
        Long id = 1L;
        BillingDetails billingDetails = new BillingDetails(/* provide necessary details */);

        // Mocking the behavior of the repository
        when(billingRepository.findById(id)).thenReturn(Optional.of(billingDetails));

        // Calling the service method
        Optional<BillingDetails> result = billingService.findAddressById(id);

        // Assertions
        assertEquals(Optional.of(billingDetails), result);
    }
    @Test
    void testFindAddressByIdWhenNotExists() {
        Long id = 2L;

        // Mocking the behavior of the repository
        when(billingRepository.findById(id)).thenReturn(Optional.empty());

        // Calling the service method
        Optional<BillingDetails> result = billingService.findAddressById(id);

        // Assertions
        assertFalse(result.isPresent());

        // Verify that the findById method was called with the specified id
        verify(billingRepository).findById(id);
    }
    @Test
    void testGetAllAddresses() {
        // Mocking the behavior of the repository
        List<BillingDetails> expectedAddresses = new ArrayList<>();
        when(billingRepository.findAll()).thenReturn(expectedAddresses);

        // Calling the service method
        List<BillingDetails> result = billingService.getAllAddresses();

        // Assertions
        assertEquals(expectedAddresses, result);

        // Verify that the findAll method was called
        verify(billingRepository).findAll();
    }
    @Test
    void testGetAllAddressesWithException() {
        // Mocking the behavior of the repository to throw an exception
        when(billingRepository.findAll()).thenThrow(new RuntimeException("Simulated exception"));

        // Calling the service method and expecting an exception
        assertThrows(RuntimeException.class, () -> billingService.getAllAddresses());

        // Verify that the findAll method was called
        verify(billingRepository).findAll();
    }
    /*@Test
    void testUpdateProfileAddressNotFound() {
        // Mocking the behavior of the repository to return an empty Optional
        BillingDetails billing=new BillingDetails();
        when(billingRepository.findById(billingDto.getId())).thenReturn(Optional.empty());

        // Calling the service method and expecting an exception
        assertThrows(EntityNotFoundException.class, () -> billingService.updateProfile(billingDto));

        // Verify that findById method was called
        verify(billingRepository).findById(billingDto.getId());
    }*/

    @Test
    void testUpdateProfileSuccess() {
        // Mocking the behavior of the repository to return an existing address

        BillingDetails existingAddress = new BillingDetails();
        when(billingRepository.findById(billingDto.getId())).thenReturn(Optional.of(existingAddress));

        // Calling the service method
        billingService.updateProfile(billingDto);

        // Verify that findById and save methods were called
        verify(billingRepository).findById(billingDto.getId());
        verify(billingRepository).save(existingAddress);
    }




}
