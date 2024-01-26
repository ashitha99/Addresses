package com.osa.Addresses.Exception;

public class BillingDetailsNotFoundException extends RuntimeException{
    public BillingDetailsNotFoundException(String message){
        super(message);
    }
}
