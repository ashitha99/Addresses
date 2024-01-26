package com.osa.Addresses.Service;

import com.osa.Addresses.Dto.ShippingDto;
import com.osa.Addresses.Entity.Shipping;
import com.osa.Addresses.Exception.AddressValidationException;
import com.osa.Addresses.Repository.ShippingRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ShippingServiceTest {
    @InjectMocks
    private ShippingService shippingService;

    @Mock
    private ShippingRepository shippingRepository;

    @Mock
    private ShippingDto shippingDto;

    @Test
    public void testSaveDetails_Success() {
        // Arrange
        Shipping shipping=new Shipping();
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

        // Mock the necessary methods in shippingDto
        when(shippingDto.getRecipientName()).thenReturn(recipientName);
        when(shippingDto.getStreetAddress()).thenReturn(streetAddress);
        when(shippingDto.getCity()).thenReturn(city);
        when(shippingDto.getState()).thenReturn(state);
        when(shippingDto.getPostalCode()).thenReturn(postalCode);
        when(shippingDto.getCountry()).thenReturn(country);
        when(shippingDto.getPhoneNo()).thenReturn(Long.valueOf(phoneNo));

        // Mock the repository's save method to return a dummy Shipping object
        when(shippingRepository.save(any(Shipping.class))).thenReturn(new Shipping());

        // Act
        Shipping savedShipping = shippingService.saveDetails(shippingDto);

        // Assert
        assertNotNull(savedShipping);

        // Verify that the repository's save method is called with the expected Shipping object
        verify(shippingRepository, times(1)).save(any(Shipping.class));
    }

    // User enters an address with missing details
    //The system should detect the incomplete address, respond with an appropriate error message, and guide the user to provide the missing information
    @Test
    public void testSaveDetails_emptyRecipientName_Exception() {
        // Arrange

        Shipping shipping=new Shipping();
        // Test data setup
        Long Id = 1L;
        Long billingId = 1L;
        Long orderId = 1L;
        String recipientName = "ash";
        String streetAddress = "A9 first floar";
        String city = "Bangalore";
        String state = "Karnataka";
        String postalCode = "560032";
        String country = "India";
        String phoneNo = "12434545135";

        // Set up shippingDto with empty streetAddress
        when(shippingDto.getRecipientName()).thenReturn("");

        // Act and Assert
        AddressValidationException exception = assertThrows(AddressValidationException.class, () -> shippingService.saveDetails(shippingDto));

        // Assert that the exception message contains the expected error message
        assertTrue(exception.getMessage().contains("Please enter the Recipient's name"));

        // Verify that the repository's save method is not called
        verify(shippingRepository, never()).save(any(Shipping.class));
    }
    @Test
    public void testFindAddressById_AddressFound() {
        // Arrange
        Long shippingId = 1L;
        Shipping expectedShipping = new Shipping();
        expectedShipping.setId(shippingId);

        // Mock the repository's findById method to return the expected Shipping object
        when(shippingRepository.findById(shippingId)).thenReturn(Optional.of(expectedShipping));

        // Act
        Optional<Shipping> foundShipping = shippingService.findAddressById(shippingId);

        // Assert
        assertTrue(foundShipping.isPresent());
        assertEquals(expectedShipping, foundShipping.get());

        // Verify that the repository's findById method is called with the expected ID
        verify(shippingRepository, times(1)).findById(shippingId);
    }
    @Test
    public void testFindAddressById_AddressNotFound() {
        // Arrange
        Long shippingId = 1L;

        // Mock the repository's findById method to return an empty Optional (simulating not found)
        when(shippingRepository.findById(shippingId)).thenReturn(Optional.empty());

        // Act
        Optional<Shipping> foundShipping = shippingService.findAddressById(shippingId);

        // Assert
        assertTrue(foundShipping.isEmpty());

        // Verify that the repository's findById method is called with the expected ID
        verify(shippingRepository, times(1)).findById(shippingId);
    }
    //After entering valid information, the system stores and associates the address with the order.
    // Verify that the validated address is securely stored and correctly associated with the specific order.
  /*  @Test
    public void testGetShippingDetailsByOrderId_DetailsFound() {
        // Arrange
        Long orderId = 1L;
        Shipping shipping1 = new Shipping();
        List<Shipping> expectedShippingList = new ArrayList<>();

        // Mock the repository's findByOrderId method to return the expected list of Shipping objects
        when(shippingRepository.findByOrderId(orderId)).thenReturn(expectedShippingList);

        // Act
        List<Shipping> foundShippingList = shippingService.getShippingDetailsByOrderId(orderId);

        // Assert
        assertEquals(expectedShippingList, foundShippingList);

        // Verify that the repository's findByOrderId method is called with the expected order ID
        verify(shippingRepository, times(1)).findByOrderId(orderId);
    }
    @Test
    public void testGetShippingDetailsByOrderId_DetailsNotFound() {
        // Arrange
        Long orderId = 1L;

        // Mock the repository's findByOrderId method to return an empty list (simulating not found)
        when(shippingRepository.findByOrderId(orderId)).thenReturn(Arrays.asList());

        // Act
        List<Shipping> foundShippingList = shippingService.getShippingDetailsByOrderId(orderId);

        // Assert
        assertTrue(foundShippingList.isEmpty());

        // Verify that the repository's findByOrderId method is called with the expected order ID
        verify(shippingRepository, times(1)).findByOrderId(orderId);
    }
    @Test
    public void testGetShippingDetailsByBillingId_NoShippingDetailsFound_ShouldReturnEmptyList() {
        // Arrange
        Long billingId = 1L;

        // Mock the repository's findByBillingId method to return an empty list (no shipping details found)
        when(shippingRepository.findByBillingId(billingId)).thenReturn(Arrays.asList());

        // Act
        List<Shipping> resultShippingList = shippingService.getShippingDetailsByBillingId(billingId);

        // Assert
        verify(shippingRepository, times(1)).findByBillingId(billingId);

        assertNotNull(resultShippingList);
        assertTrue(resultShippingList.isEmpty());
    }
    @Test
    public void testGetShippingDetailsByBillingId_ShouldReturnShippingList() {
        // Arrange
        Long billingId = 1L;

        Shipping shipping1 = new Shipping();
        shipping1.setId(1L);
        shipping1.setBillingId(billingId);

        Shipping shipping2 = new Shipping();
        shipping2.setId(2L);
        shipping2.setBillingId(billingId);

        List<Shipping> expectedShippingList = Arrays.asList(shipping1, shipping2);

        // Mock the repository's findByBillingId method to return the expected shipping list
        when(shippingRepository.findByBillingId(billingId)).thenReturn(expectedShippingList);

        // Act
        List<Shipping> resultShippingList = shippingService.getShippingDetailsByBillingId(billingId);

        // Assert
        verify(shippingRepository, times(1)).findByBillingId(billingId);

        assertNotNull(resultShippingList);
        assertEquals(expectedShippingList.size(), resultShippingList.size());
        assertEquals(expectedShippingList, resultShippingList);
    }

    @Test
    public void testGetShippingDetailsByBillingId() {
        // Arrange
        Long billingId = 1L;

        Shipping shipping1 = new Shipping();
        shipping1.setId(1L);
        shipping1.setBillingId(billingId);

        Shipping shipping2 = new Shipping();
        shipping2.setId(2L);
        shipping2.setBillingId(billingId);

        List<Shipping> expectedShippingList = Arrays.asList(shipping1, shipping2);

        // Mock the repository's findByBillingId method to return the expected shipping list
        when(shippingRepository.findByBillingId(billingId)).thenReturn(expectedShippingList);

        // Act
        List<Shipping> resultShippingList = shippingService.getShippingDetailsByBillingId(billingId);

        // Assert
        verify(shippingRepository, times(1)).findByBillingId(billingId);

        assertNotNull(resultShippingList);
        assertEquals(expectedShippingList.size(), resultShippingList.size());
        assertEquals(expectedShippingList, resultShippingList);
    }*/
    @Test
    public void testGetAllAddresses_ShouldReturnAllShippingAddresses() {
        // Arrange
        Shipping shipping1 = new Shipping();
        shipping1.setId(1L);

        Shipping shipping2 = new Shipping();
        shipping2.setId(2L);

        List<Shipping> expectedShippingList = Arrays.asList(shipping1, shipping2);

        // Mock the repository's findAll method to return the expected shipping list
        when(shippingRepository.findAll()).thenReturn(expectedShippingList);

        // Act
        List<Shipping> resultShippingList = shippingService.getAllAddresses();


        assertNotNull(resultShippingList);
        assertEquals(expectedShippingList.size(), resultShippingList.size());
        assertEquals(expectedShippingList, resultShippingList);

        // Assert
        verify(shippingRepository, times(1)).findAll();
    }

    @Test
    void testUpdateProfileSuccess() {

        Shipping existingAddress = new Shipping();


        // Mocking the behavior of the repository to return an existing address
        when(shippingRepository.findById(shippingDto.getId())).thenReturn(Optional.of(existingAddress));

        // Calling the service method
        shippingService.updateProfile(shippingDto);

        // Verify that findById and save methods were called
        verify(shippingRepository).findById(shippingDto.getId());
        verify(shippingRepository).save(existingAddress);
    }


}
