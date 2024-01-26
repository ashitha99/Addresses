package com.osa.Addresses.Controller;

import com.osa.Addresses.Dto.UserDto;
import com.osa.Addresses.Entity.User;
import com.osa.Addresses.Service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
    @InjectMocks
    private UserController userController;
    @Mock
    private UserService userService;

    @Mock
    private UserDto userRequest;

    @Test
    public void testCreateUser_Success() {
        // Given
        String userName="ash";
        String email="ash@gmail.com";


        User createdUser = new User();
        createdUser.setId(1L);
        // You can set other properties of the createdUser as needed

        when(userService.createUser(userRequest)).thenReturn(createdUser);

        // When
        ResponseEntity<User> responseEntity = userController.createUser(userRequest);

        // Then
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

        User responseBody = responseEntity.getBody();
        assertNotNull(responseBody);

        verify(userService, times(1)).createUser(userRequest);
    }

}
