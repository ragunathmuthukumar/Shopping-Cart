package com.shopping.cart;

public class Product {
    private final String productName;
    private final double pricePerUnit;
    private final double taxRate;
    private double quantity;

    public Product(String productName, double quantity, double pricePerUnit, double taxRate) {
	this.productName = productName;
	this.quantity = quantity;
	this.pricePerUnit = pricePerUnit;
	this.taxRate = taxRate;
    }

    public void addQuantity(double quantity) {
	this.quantity += quantity;
    }

    public String getProductName() {
	return productName;
    }

    public double getQuantity() {
	return quantity;
    }

    public double getPricePerUnit() {
	return pricePerUnit;
    }

    public double getTaxRate() {
	return taxRate;
    }
}
