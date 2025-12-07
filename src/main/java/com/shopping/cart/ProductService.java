package com.shopping.cart;

public class ProductService {
    private final String FORBIDDEN_PATTERN = ".*[/\\\\?%#*:<>|\".].*";

    public void validateCartItem(String itemName, double quantity) {
	validateProductName(itemName);
	vlidateQunality(quantity);

    }

    public void validateProductName(String itemName) {
	if (null == itemName || itemName.trim().isEmpty()) {
	    throw new IllegalArgumentException("Product name cannot be empty");
	}
	if (itemName.matches(FORBIDDEN_PATTERN)) {
	    throw new IllegalArgumentException("Product name contains forbidden characters");
	}
    }

    public void vlidateQunality(double quantity) {
	if (quantity <= 0) {
	    throw new IllegalArgumentException("Quantity must be greater than zero");
	}
    }
}