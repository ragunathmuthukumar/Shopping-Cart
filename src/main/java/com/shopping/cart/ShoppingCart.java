package com.shopping.cart;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.shopping.client.PriceClient;
import com.shopping.validation.CartItemValidator;

public class ShoppingCart {
    private final Map<String, CartItem> products;
    private final PriceClient pricingService;
    private final CartItemValidator cartItemValidator;
    private double subTotal;
    private double totalTaxes;
    private double totalPrice;

    public ShoppingCart() {
	products = new HashMap<>();
	pricingService = new PriceClient();
	cartItemValidator = new CartItemValidator();
    }

    public void addProduct(String productName, int quantity) {
	cartItemValidator.validateCartItem(productName, quantity);
	addProductIntoCart(productName, quantity);
    }

    private void addProductIntoCart(String productName, int quantity) {
	if (products.containsKey(productName)) {
	    products.get(productName).addQuantity(quantity);
	} else {
	    double pricePerUnit = getProductUnitPrice(productName);
	    products.put(productName, new CartItem(productName, quantity, pricePerUnit, 0.125));
	}

	if (!products.isEmpty()) {
	    calculateCartPrice();
	}
    }

    private double getProductUnitPrice(String productName) {
	return pricingService.getPrice(productName);
    }

    public Map<String, CartItem> getProducts() {
	return Collections.unmodifiableMap(this.products);
    }

    public double getSubTotal() {
	return subTotal;
    }

    public double getTotalTaxes() {
	return totalTaxes;
    }

    public double getTotalPrice() {
	return totalPrice;
    }

    private void calculateCartPrice() {
	calculateSubTotal();
	calculateTotalTaxes();
	calculateTotalPrice();
    }

    private void calculateSubTotal() {
	subTotal = round(getProducts().values().stream()
		.mapToDouble(cartItem -> cartItem.getPricePerUnit() * cartItem.getQuantity()).sum());
    }

    private void calculateTotalTaxes() {
	totalTaxes = round(getProducts().values().stream()
		.mapToDouble(cartItem -> cartItem.getPricePerUnit() * cartItem.getQuantity() * cartItem.getTaxRate())
		.sum());
    }

    private void calculateTotalPrice() {
	totalPrice = round(subTotal + totalTaxes);
    }

    private double round(double value) {
	double scale = Math.pow(10, 2);
	return Math.round(value * scale) / scale;
    }
}