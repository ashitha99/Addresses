package com.osa.Addresses.Service;

import com.osa.Addresses.Dto.BillingDto;
import com.osa.Addresses.Dto.ShippingDto;
import com.osa.Addresses.Dto.UserDto;
import com.osa.Addresses.Entity.BillingDetails;
import com.osa.Addresses.Entity.Shipping;
import com.osa.Addresses.Entity.User;
import com.osa.Addresses.Exception.BillingDetailsNotFoundException;
import com.osa.Addresses.Exception.ShippingDetailsNotFoundException;
import com.osa.Addresses.Repository.BillingRepository;
import com.osa.Addresses.Repository.ShippingRepository;
import com.osa.Addresses.Repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BillingRepository billingRepository;

    @Mock
    private ShippingRepository shippingRepository;

    @Mock
    private UserDto userRequest;

    @Mock
    private BillingDto billingDto;
    @Mock
    private ShippingDto shippingDto;


    @Test
    public void testCreateUser_Success() {
        // Given
        String username="ash";
        String email="ash@gmail.com";
        Long shippingId=1L;
        Long billingId=1L ;

        User newUser=new User();
        BillingDetails billingDetails=new BillingDetails();
        Shipping shipping=new Shipping();


        when(billingRepository.findById(1L)).thenReturn(Optional.of(billingDetails));
        when(shippingRepository.findById(1L)).thenReturn(Optional.of(shipping));
        when(userRepository.save(any(User.class))).thenReturn(newUser);

        UserDto userRequest = Mockito.mock(UserDto.class);
        when(userRequest.getUserName()).thenReturn(username);
        when(userRequest.getEmailAddress()).thenReturn(email);
        when(userRequest.getShippingId()).thenReturn(shippingId);
        when(userRequest.getBillingId()).thenReturn(billingId);


        // When
        User createdUser = userService.createUser(userRequest);

        // Then
        assertNotNull(createdUser);
        assertEquals(newUser.getId(), createdUser.getId());
        assertEquals(newUser.getUserName(), createdUser.getUserName());
        assertEquals(newUser.getEmailAddress(), createdUser.getEmailAddress());

        verify(billingRepository, times(1)).findById(1L);
        verify(shippingRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testCreateUser_Failure_BillingNotFound() {

        String username="ash";
        String email="ash@gmail.com";
        Long shippingId=1L;
       // Long billingId=1L ;
        // Given
        UserDto userRequest = Mockito.mock(UserDto.class);
        userRequest.setUserName("jane");
        userRequest.setEmailAddress("jane@example.com");
       // userRequest.setBillingId(999L); // Use a non-existing billing ID
        userRequest.setShippingId(2L);

        User user=new User();
        Shipping shipping=new Shipping();

        when(billingRepository.findById(anyLong())).thenReturn(Optional.empty());

        // When/Then
        BillingDetailsNotFoundException exception = assertThrows(BillingDetailsNotFoundException.class, () -> {
            userService.createUser(userRequest);
        });

        // Assert
        assertEquals("Billing not found with id: 0", exception.getMessage());
        verify(shippingRepository, never()).findById(anyLong());
        verify(userRepository, never()).save(any(User.class));
    }

    /*@Test
    public void testCreateUser_Failure_ShippingNotFound() {
        String username="ash";
        String email="ash@gmail.com";
        //Long shippingId=1L;
        Long billingId=1L ;
        // Given
        UserDto userRequest = Mockito.mock(UserDto.class);
        when(userRequest.getUserName()).thenReturn(username);  // Correct way to set values on mock
        when(userRequest.getEmailAddress()).thenReturn(email);
        when(userRequest.getBillingId()).thenReturn(billingId);
        when(userRequest.getShippingId()).thenReturn(null);
        //userRequest.setShippingId(888L); // Use a non-existing shipping ID

        //when(billingRepository.findById(1L)).thenReturn(Optional.of(new BillingDetails()));
        when(shippingRepository.findById(anyLong())).thenReturn(Optional.empty());

        // When/Then
        ShippingDetailsNotFoundException exception = assertThrows(ShippingDetailsNotFoundException.class, () -> {
            userService.createUser(userRequest);
        });

        // Assert
        assertEquals("Shipping not found with id: 0", exception.getMessage());
        verify(userRepository, never()).save(any(User.class));
    }*/

}
