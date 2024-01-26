package com.osa.Addresses.Exception;

public class ShippingDetailsNotFoundException extends RuntimeException{
    public ShippingDetailsNotFoundException(String message){
        super(message);
    }
}
