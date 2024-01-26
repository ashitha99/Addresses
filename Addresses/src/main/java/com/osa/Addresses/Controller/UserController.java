package com.osa.Addresses.Controller;

import com.osa.Addresses.Dto.UserDto;
import com.osa.Addresses.Entity.User;
import com.osa.Addresses.Service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/addressMs")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(BillingController.class);

    @Autowired
    private UserService userService;

    @PostMapping("/user")
    public ResponseEntity<User> createUser(@RequestBody UserDto userRequest) {
        logger.info("Creating UserDetails with request: {}",userRequest );
        User createdUser = userService.createUser(userRequest);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }


}
