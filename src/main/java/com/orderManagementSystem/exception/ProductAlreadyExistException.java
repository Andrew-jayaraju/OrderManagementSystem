package com.orderManagementSystem.exception;

public class ProductAlreadyExistException extends RuntimeException{

    public ProductAlreadyExistException(String message){
        super(message);
    }

}
