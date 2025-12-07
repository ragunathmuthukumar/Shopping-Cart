package com.shopping.validation;

import com.shopping.exception.InvalidCartItemException;

public class CartItemValidator {
    private final String FORBIDDEN_PATTERN = ".*[/\\\\?%#*:<>|\".].*";

    public void validateCartItem(String itemName, int quantity) {
	validateProductName(itemName);
	validateQuality(itemName, quantity);
    }

    public void validateProductName(String itemName) {
	if (null == itemName || itemName.trim().isEmpty()) {
	    throw new InvalidCartItemException("CartItem name cannot be empty");
	}
	if (itemName.matches(FORBIDDEN_PATTERN)) {
	    throw new InvalidCartItemException("CartItem name contains forbidden characters");
	}
    }

    public void validateQuality(String itemName, int quantity) {
	if (quantity < 1) {
	    throw new InvalidCartItemException(itemName, "Quantity must be greater than zero");
	}
    }
}