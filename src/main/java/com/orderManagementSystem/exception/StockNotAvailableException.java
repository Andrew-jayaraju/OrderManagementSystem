package com.orderManagementSystem.exception;

public class StockNotAvailableException extends RuntimeException{

    public StockNotAvailableException(String message){
        super(message);
    }
}
