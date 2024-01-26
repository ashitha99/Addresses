package com.osa.Addresses.Controller;

import com.osa.Addresses.Dto.OrderRequest;
import com.osa.Addresses.Entity.Orders;
import com.osa.Addresses.Service.OrdersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/addressMs")
public class OrdersController {
    private static final Logger logger = LoggerFactory.getLogger(OrdersController.class);

    @Autowired
    private OrdersService orderService;

    @PostMapping("/order")
    public ResponseEntity<Orders> createOrder(@RequestBody OrderRequest orderRequest) {
        Orders createdOrder = orderService.createOrder(orderRequest);
        logger.info("Order created successfully: {}", createdOrder);
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }



}
