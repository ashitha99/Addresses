package com.osa.Addresses.Service;

import com.osa.Addresses.Dto.OrderRequest;
import com.osa.Addresses.Entity.Orders;
import com.osa.Addresses.Entity.Shipping;
import com.osa.Addresses.Exception.DateNotFoundException;
import com.osa.Addresses.Exception.ProductNotFoundException;
import com.osa.Addresses.Exception.ShippingDetailsNotFoundException;
import com.osa.Addresses.Repository.OrdersRepository;
import com.osa.Addresses.Repository.ShippingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrdersService {
    @Autowired
    private OrdersRepository orderRepository;

   @Autowired
   private ShippingRepository shippingRepository;



    public Orders createOrder(OrderRequest orderRequest) {


        if (orderRequest.getProductName() == null || orderRequest.getProductName().isEmpty()) {
            throw new ProductNotFoundException("Product Name is missing");
        }
        if (orderRequest.getDate() == null) {
            throw new DateNotFoundException("Date is missing");
        }
        Orders order = new Orders();
        order.setProductName(orderRequest.getProductName());
        order.setDate(orderRequest.getDate());

        if (orderRequest.getShippingId() != null) {
            // Fetch existing Shipping entity
            Shipping shipping = shippingRepository.findById(orderRequest.getShippingId())
                    .orElseThrow(() -> new ShippingDetailsNotFoundException("Shipping not found with id: " + orderRequest.getShippingId()));
            order.setShipping(shipping);
        }
        return orderRepository.save(order);

    }
    }
