package com.shopping.exception;

public class InvalidCartItemException extends RuntimeException {
    public InvalidCartItemException(String cartItem, String message) {
	super(cartItem + " is not a valid cart item." + message);
    }

    public InvalidCartItemException(String message) {
        super(message);
    }
}