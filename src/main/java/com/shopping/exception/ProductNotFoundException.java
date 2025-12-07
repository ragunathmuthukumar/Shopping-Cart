package com.shopping.exception;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String productName) {
	super(productName + " not found");
    }
}