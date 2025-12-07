package com.shopping.cart;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.shopping.client.PriceClient;
import com.shopping.validation.CartItemValidator;

public class ShoppingCart {
    private static final double TAX_RATE = 0.125;
    private static final int ROUND_SCALE = 2;
    private final Map<String, CartItem> products;
    private final PriceClient pricingService;
    private final CartItemValidator cartItemValidator;
    private double subTotal;
    private double totalTaxes;
    private double totalPrice;

    public ShoppingCart() {
	products = new ConcurrentHashMap<>();
	pricingService = new PriceClient();
	cartItemValidator = new CartItemValidator();
    }

    public void addProduct(String productName, int quantity) {
	cartItemValidator.validateQuantity(productName, quantity);
	addProductIntoCart(productName, quantity);
    }

    private void addProductIntoCart(String productName, int quantity) {
	if (null != productName && products.containsKey(productName)) {
	    products.get(productName).addQuantity(quantity);
	} else {
	    double pricePerUnit = getProductUnitPrice(productName);
	    products.put(productName, new CartItem(productName, quantity, pricePerUnit, TAX_RATE));
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
	subTotal = round(getProducts().values().stream().mapToDouble(CartItem::getItemTotalPrice).sum());
    }

    private void calculateTotalTaxes() {
	totalTaxes = round(getProducts().values().stream().mapToDouble(CartItem::getItemTotaltax).sum());
    }

    private void calculateTotalPrice() {
	totalPrice = round(getProducts().values().stream().mapToDouble(CartItem::getItemTotalPriceWithTax).sum());
    }

    private double round(double value) {
	double scale = Math.pow(10, ROUND_SCALE);
	return Math.round(value * scale) / scale;
    }
}