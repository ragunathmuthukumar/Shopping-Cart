package com.shopping.cart;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ShoppingCart {

    private final Map<String, Product> products;
    private final PricingService pricingService;
    private final ProductService productService;
    private double subTotal;
    private double totalTaxes;
    private double totalPrice;

    public ShoppingCart() {
	products = new HashMap<>();
	pricingService = new PricingService();
	productService = new ProductService();
    }

    public void addProduct(String productName, double quantity) {
	try {
	    productService.validateCartItem(productName, quantity);
	    addProductIntoCart(productName, quantity);
	} catch (Exception e) {
	    System.out.println(
		    "Error while adding product " + productName + " to shopping cart. Error " + e.getMessage());
	}
    }

    private void addProductIntoCart(String productName, double quantity) {
	if (products.containsKey(productName)) {
	    products.get(productName).addQuantity(quantity);
	} else {
	    double pricePerUnit = getProductUnitPrice(productName);
	    products.put(productName, new Product(productName, quantity, pricePerUnit, 0.125));
	}

	if (!products.isEmpty()) {
	    calculateCartPrice();
	}
    }

    private double getProductUnitPrice(String productName) {
	return pricingService.getProductPrice(productName);
    }

    public Map<String, Product> getProducts() {
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
		.mapToDouble(product -> product.getPricePerUnit() * product.getQuantity()).sum());
    }

    private void calculateTotalTaxes() {
	totalTaxes = round(getProducts().values().stream()
		.mapToDouble(product -> product.getPricePerUnit() * product.getQuantity() * product.getTaxRate())
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