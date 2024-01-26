package com.osa.Addresses.Controller;

import com.osa.Addresses.Dto.ShippingDto;
import com.osa.Addresses.Entity.Shipping;
import com.osa.Addresses.Exception.AddressValidationException;
import com.osa.Addresses.Service.ShippingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class ShippingControllerTest {
    @InjectMocks
    private ShippingController shippingController;
    @Mock
    private ShippingService shippingService;

    private ShippingDto shippingDto;

    //User enters complete and accurate shipping information.
    //The system accepts the input, validates the address, securely stores the information, and associates it with the order.
    @Test
    public void testCreateShipping_Success() {

        //  test data setup here

        Long Id = 1L;
        Long billingId = 1L;
        Long orderId = 1L;
        String recipientName = "JohnDoe";
        String streetAddress = "A9-1st floar";
        String city = "Bangalore";
        String state = "Karnataka";
        String postalCode = "560032";
        String country = "India";
        String phoneNo = "12434545135";

        Shipping createdShipping = new Shipping();


        // Mock the service method to return a Shipping instance
        when(shippingService.saveDetails(shippingDto)).thenReturn(createdShipping);

        // Call the controller method
        ResponseEntity<Shipping> responseEntity = shippingController.createShipping(shippingDto);

        // Assert the result
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(createdShipping, responseEntity.getBody());

        // Verify that the service method was called with the correct argument
        verify(shippingService, times(1)).saveDetails(shippingDto);
    }

   /* @Test
    public void testCreateShipping_ValidationFailure() {

        //  test data setup here
        Long Id = 1L;
        Long billingId = 1L;
        Long orderId = 1L;
        String recipientName = "JohnDoe";
        String streetAddress = "A9-1st floar";
        String city = "Bangalore";
        String state = "Karnataka";
        String postalCode = "560032";
        String country = "India";
        String phoneNo = "12434545135";

        Shipping shipping = new Shipping();

        // Mock the service method to throw AddressValidationException
        when(shippingService.saveDetails(shippingDto)).thenThrow(new AddressValidationException("Validation Error"));

        // Call the controller method
        ResponseEntity<Shipping> responseEntity = shippingController.createShipping(shippingDto);

        // Assert the result for a validation failure
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());

        // Verify that the service method was called with the correct argument
        verify(shippingService, times(1)).saveDetails(shippingDto);
    }*/

    @Test
    public void testGetAddressById_Success() {
        //  test data setup here
        Long addressId = 1L;
        Shipping expectedAddress = new Shipping();

        // Mock the service method to return an Optional containing the expected address
        when(shippingService.findAddressById(addressId)).thenReturn(Optional.of(expectedAddress));

        // Call the controller method
        Optional<Shipping> result = shippingController.getAddressById(addressId);

        // Assert the result for a successful scenario
        assertTrue(result.isPresent());
        assertEquals(expectedAddress, result.get());

        // Verify that the service method was called with the correct argument
        verify(shippingService, times(1)).findAddressById(addressId);
    }

    @Test
    public void testGetAddressById_AddressNotFound() {
        // Test data setup here
        Long addressId = 1L;

        // Mock the service method to return an empty Optional (address not found)
        when(shippingService.findAddressById(addressId)).thenReturn(Optional.empty());

        // Call the controller method
        Optional<Shipping> result = shippingController.getAddressById(addressId);

        // Assert the result for the failure scenario
        assertFalse(result.isPresent()); // Ensure the result is empty

        // Verify that the service method was called with the correct argument
        verify(shippingService, times(1)).findAddressById(addressId);
    }

    @Test
    public void testUpdateAddress_Success() {
        // Test data setup here

        Long Id = 1L;
        Long billingId = 1L;
        Long orderId = 1L;
        String recipientName = "JohnDoe";
        String streetAddress = "A9-1st foar";
        String city = "Bangalore";
        String state = "Karnataka";
        String postalCode = "560032";
        String country = "India";
        String phoneNo = "12434545135";

        Shipping updatedShipping = new Shipping();


        // Call the updateAddress method
        ResponseEntity<?> response = shippingController.updateAddress(shippingDto);

        // Assert that the response is HttpStatus.OK
        assertEquals(HttpStatus.OK, response.getStatusCode());

        verify(shippingService, times(1)).updateProfile(shippingDto);
    }

   /* @Test
    public void testUpdateAddress_Failure() {
        // Test data setup
        Long Id = 1L;
        Long billingId = 1L;
        Long orderId = 1L;
        String recipientName = "JohnDoe";
        String streetAddress = "A9-1st floor";
        String city = "Bangalore";
        String state = "Karnataka";
        String postalCode = "560032";
        String country = "India";
        String phoneNo = "12434545135";

        Shipping shipping=new Shipping();
        // Mock the behavior of the shippingService to throw an exception
        doThrow(new RuntimeException("Simulated failure")).when(shippingService).updateProfile(shippingDto);

        // Call the updateAddress method
        ResponseEntity<?> response = shippingController.updateAddress(shippingDto);

        // Assert that the response is HttpStatus.BAD_REQUEST (or another appropriate failure status)
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        // Verify that the updateProfile method is called with the expected shippingDto
        verify(shippingService, times(1)).updateProfile(shippingDto);
    }*/
   @Test
   public void testDeleteBillingDetail_Success() {
       // Given
       Long id = 1L;

       doNothing().when(shippingService).deleteById(id);

       shippingController.deleteBillingDetail(id);

       verify(shippingService, times(1)).deleteById(id);
   }

}
