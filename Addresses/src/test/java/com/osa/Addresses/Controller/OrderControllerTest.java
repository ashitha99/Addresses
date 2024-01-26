package com.osa.Addresses.Controller;

import com.osa.Addresses.Dto.OrderRequest;
import com.osa.Addresses.Entity.Orders;
import com.osa.Addresses.Service.OrdersService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderControllerTest {

    @InjectMocks
    private OrdersController ordersController;

    @Mock
    private OrdersService orderService;

    private OrderRequest orderRequest;

    @Test
    public void testCreateOrder_Success() {

        Long id=1L;
        String productName="phone";
        LocalDate date= LocalDate.parse("2023-02-02");
        Long billingId=1L;
        Long shippingId=1L;

        Orders createdOrder = new Orders(); // Assuming your createOrder method returns an Orders object

        // Mocking the service method
        when(orderService.createOrder(orderRequest)).thenReturn(createdOrder);

        // When
        ResponseEntity<Orders> responseEntity = ordersController.createOrder(orderRequest);

        // Then

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(createdOrder, responseEntity.getBody());

        verify(orderService, times(1)).createOrder(orderRequest);
    }




}
