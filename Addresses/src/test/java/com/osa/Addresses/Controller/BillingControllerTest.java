package com.osa.Addresses.Controller;

import com.osa.Addresses.Dto.BillingDto;
import com.osa.Addresses.Entity.BillingDetails;
import com.osa.Addresses.Entity.Shipping;
import com.osa.Addresses.Exception.AddressValidationException;
import com.osa.Addresses.Service.BillingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BillingControllerTest {
    @InjectMocks
    private BillingController billingController;
    @Mock
    private BillingService billingService;

    private BillingDto billingDto;



    @Test
    public void testCreateDetails_Success() {

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

        BillingDetails expectedBillingDetails = new BillingDetails();

        when(billingService.saveDetails(billingDto)).thenReturn(expectedBillingDetails);

        // Act
        ResponseEntity<BillingDetails> resultResponseEntity = billingController.createDetails(billingDto);
        BillingDetails resultBillingDetails = resultResponseEntity.getBody();

        // Assert the result
        assertEquals(HttpStatus.CREATED, resultResponseEntity.getStatusCode());
        assertEquals(expectedBillingDetails, resultBillingDetails);

        // Verify that the billingService's saveDetails method is called with the expected BillingDto
        verify(billingService, times(1)).saveDetails(billingDto);
    }
   /* @Test
    public void testCreateDetails_Failure() {
        Long id = 1L;
        Long customerId = 1L;
        Long billingId = 1L;
        String recipientName = "";
        String streetAddress = "A9-sri sai gruha APTS";
        String city = "";
        String state = "karnatka";
        String postalCode = "5606032";
        String country = "India";
        String email = "ash@gmil.com";
        Long phoneNo = 9844558347L;
        String paymentType = "";
        String cardHolderName = "Ashi";
        String creditCardNumber = "45678901344534";
        String expirationDate = "2023-04-02";

        // Mock the billingService's saveDetails method to throw an exception
        when(billingService.saveDetails(billingDto)).thenThrow(new AddressValidationException("Some error message"));

        // Act
        ResponseEntity<BillingDetails> resultResponseEntity = billingController.createDetails(billingDto);
        BillingDetails resultBillingDetails = resultResponseEntity.getBody();

        // Assert the result
        assertEquals(HttpStatus.BAD_REQUEST, resultResponseEntity.getStatusCode());
        assertNull(resultBillingDetails);

        // Verify that the billingService's saveDetails method is called with the expected BillingDto
        verify(billingService, times(1)).saveDetails(billingDto);
    }*/

    @Test
    public void testGetAddressById_Success() {
        // Test data setup
        Long id = 1L;

        // Create a dummy BillingDetails object for the expected result
        BillingDetails expectedBillingDetails = new BillingDetails();
        expectedBillingDetails.setId(id);

        // Mock the billingService's findAddressById method to return the dummy BillingDetails object
        when(billingService.findAddressById(id)).thenReturn(Optional.of(expectedBillingDetails));

        // Act
        Optional<BillingDetails> resultBillingDetails = billingController.getAddressById(id);

        // Assert
        assertTrue(resultBillingDetails.isPresent());
        assertEquals(expectedBillingDetails, resultBillingDetails.get());

        // Verify that the billingService's findAddressById method is called with the expected ID
        verify(billingService, times(1)).findAddressById(id);
    }
    @Test
    public void testGetAddressById_NotFound() {
        // Test data setup
        Long id = 1L;

        // Mock the billingService's findAddressById method to return an empty optional
        when(billingService.findAddressById(id)).thenReturn(Optional.empty());

        // Act
        Optional<BillingDetails> resultBillingDetails = billingController.getAddressById(id);

        // Assert
        assertTrue(resultBillingDetails.isEmpty());

        // Verify that the billingService's findAddressById method is called with the expected ID
        verify(billingService, times(1)).findAddressById(id);
    }
    @Test
    public void testGetAllAddresses_Success() {
        // Test data setup
        BillingDetails billingDetails1 = new BillingDetails();
        BillingDetails billingDetails2 = new BillingDetails();

        // Mock the billingService's getAllAddresses method to return a list of billing details
        when(billingService.getAllAddresses()).thenReturn(List.of(billingDetails1, billingDetails2));

        // Act
        List<BillingDetails> resultBillingDetailsList = billingController.getAllAddresses();

        // Assert
        assertFalse(resultBillingDetailsList.isEmpty());
        assertEquals(2, resultBillingDetailsList.size()); // Adjust the expected size based on your mock data

        // Verify that the billingService's getAllAddresses method is called
        verify(billingService, times(1)).getAllAddresses();
    }
    @Test
    public void testGetAllAddresses_Failure() {
        // Mock the billingService's getAllAddresses method to return an empty list
        when(billingService.getAllAddresses()).thenReturn(Collections.emptyList());

        // Act
        List<BillingDetails> resultBillingDetailsList = billingController.getAllAddresses();

        // Assert
        assertTrue(resultBillingDetailsList.isEmpty());

        // Verify that the billingService's getAllAddresses method is called
        verify(billingService, times(1)).getAllAddresses();
    }
    @Test
    public void testDeleteBillingDetail_Success() {
        // Test data setup
        Long billingIdToDelete = 1L;

        // Mock the billingService's deleteById method
        doNothing().when(billingService).deleteById(billingIdToDelete);

        // Act
        billingController.deleteBillingDetail(billingIdToDelete);

        // Add assertions to ensure that the method execution was successful

        assertTrue(true);

        // Verify that the billingService's deleteById method is called with the expected billingId
        verify(billingService, times(1)).deleteById(billingIdToDelete);
    }
    @Test
    public void testUpdateAddress_Success() {
        // Test data setup here

        Long Id = 1L;
        String recipientName = "JohnDoe";
        String streetAddress = "A9-1st foar";
        String city = "Bangalore";
        String state = "Karnataka";
        String postalCode = "560032";
        String country = "India";
        String phoneNo = "12434545135";

        BillingDetails updatedDetails = new BillingDetails();


        // Call the updateAddress method
        ResponseEntity<?> response = billingController.updateAddress(billingDto);

        // Assert that the response is HttpStatus.OK
        assertEquals(HttpStatus.OK, response.getStatusCode());

        verify(billingService, times(1)).updateProfile(billingDto);
    }

    @Test
    public void testDeleteBillingDetail_Failure() {
        // Test data setup
        Long billingIdToDelete = 1L;

        // Mock the billingService's deleteById method to throw an exception
        doThrow(new RuntimeException("Deletion failed")).when(billingService).deleteById(billingIdToDelete);

        // Act and Assert
        assertThrows(RuntimeException.class, () -> billingController.deleteBillingDetail(billingIdToDelete));

        // Verify that the billingService's deleteById method is called with the expected billingId
        verify(billingService, times(1)).deleteById(billingIdToDelete);
    }
   /* @Test
    public void testUpdateAddress_Failure() {
        // Test data setup
        BillingDetails billing=new BillingDetails();

        // Mock the billingService's updateProfile method to throw an exception
        doThrow(new RuntimeException("Invalid data")).when(billingService).updateProfile(billingDto);

        // Act
        ResponseEntity<?> responseEntity = billingController.updateAddress(billingDto);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Invalid data", responseEntity.getBody());

        // Verify that the billingService's updateProfile method is called with the expected BillingDto
        verify(billingService, times(1)).updateProfile(billingDto);
    }*/

}
