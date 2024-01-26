package com.osa.Addresses.Service;

import com.osa.Addresses.Dto.OrderRequest;
import com.osa.Addresses.Entity.Orders;
import com.osa.Addresses.Entity.Shipping;
import com.osa.Addresses.Exception.DateNotFoundException;
import com.osa.Addresses.Exception.ProductNotFoundException;
import com.osa.Addresses.Exception.ShippingDetailsNotFoundException;
import com.osa.Addresses.Repository.OrdersRepository;
import com.osa.Addresses.Repository.ShippingRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {
    @InjectMocks
    private OrdersService ordersService;

    @Mock
    private OrdersRepository ordersRepository;

    @Mock
    private ShippingRepository shippingRepository;

    @Mock
    private OrderRequest orderRequest;


    @Test
    public void testCreateOrder_Success() {
        // Given
        Long id=1L;
        String product="phone";
        LocalDate date= LocalDate.parse("2023-02-02");
        Long shippingId=1L;

        when(orderRequest.getProductName()).thenReturn(product);
        when(orderRequest.getDate()).thenReturn(date);
        when(orderRequest.getShippingId()).thenReturn(shippingId);

        Orders savedOrder = new Orders();


        when(shippingRepository.findById(any())).thenReturn(Optional.of(new Shipping())); // Mocking a valid Shipping entity
        when(ordersRepository.save(any())).thenReturn(savedOrder);

        Orders createdOrder = ordersService.createOrder(orderRequest);

        assertNotNull(createdOrder);
        assertEquals(savedOrder, createdOrder);

        verify(shippingRepository, times(1)).findById(any());
        verify(ordersRepository, times(1)).save(any());
    }

    @Test
    public void testCreateOrder_Failure_InvalidShippingId() {
        // Given
        String product = "phone";
        LocalDate date = LocalDate.parse("2023-02-02");
        Long invalidShippingId = 99L;

        // Mocking behavior of OrderRequest
        when(orderRequest.getProductName()).thenReturn(product);
        when(orderRequest.getDate()).thenReturn(date);
        when(orderRequest.getShippingId()).thenReturn(invalidShippingId);

        // Mocking behavior of repositories to simulate an invalid shippingId
        when(shippingRepository.findById(invalidShippingId)).thenReturn(Optional.empty());

        // When/Then
        ShippingDetailsNotFoundException exception = assertThrows(ShippingDetailsNotFoundException.class, () -> {
            ordersService.createOrder(orderRequest);
        });

        assertEquals("Shipping not found with id: " + invalidShippingId, exception.getMessage());
        verify(shippingRepository, times(1)).findById(invalidShippingId);
        verify(ordersRepository, never()).save(any());
    }
    @Test
    public void testCreateOrder_Failure_EmptyProductName() {
        // Given
        LocalDate date = LocalDate.parse("2023-02-02");

        // Mocking behavior of OrderRequest
        //when(orderRequest.getProductName()).thenReturn(""); // Simulating empty product name
        //when(orderRequest.getDate()).thenReturn(date);
        //when(orderRequest.getShippingId()).thenReturn(1L);

        // When/Then
        ProductNotFoundException exception = assertThrows(ProductNotFoundException.class, () -> {
            ordersService.createOrder(orderRequest);
        });

        assertEquals("Product Name is missing", exception.getMessage());
        verify(shippingRepository, never()).findById(any());
        verify(ordersRepository, never()).save(any());
    }
    @Test
    public void testCreateOrder_Failure_EmptyDate() {

        Long id=1L;
        String product="phone";

        when(orderRequest.getProductName()).thenReturn(product);

        // When/Then
        DateNotFoundException exception = assertThrows(DateNotFoundException.class, () -> {
            ordersService.createOrder(orderRequest);
        });

        assertEquals("Date is missing", exception.getMessage());
        verify(shippingRepository, never()).findById(any());
        verify(ordersRepository, never()).save(any());
    }




}
