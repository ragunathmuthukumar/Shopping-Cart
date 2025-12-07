package com.shopping.cart;

public class CartItem {
    private final String productName;
    private final double pricePerUnit;
    private final double taxRate;
    private int quantity;
    private double itemTotalPrice;
    private double itemTotalPriceWithTax;
    private double itemTotaltax;

    public CartItem(String productName, int quantity, double pricePerUnit, double taxRate) {
	this.productName = productName;
	this.quantity = quantity;
	this.pricePerUnit = pricePerUnit;
	this.taxRate = taxRate;
	calculateTotalPrice();
    }

    public void addQuantity(double quantity) {
	this.quantity += quantity;
	calculateTotalPrice();
    }

    private void calculateTotalPrice() {
	itemTotalPrice = quantity * pricePerUnit;
	itemTotaltax = itemTotalPrice * taxRate;
	itemTotalPriceWithTax = itemTotalPrice + itemTotaltax;
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

    public double getItemTotalPrice() {
	return itemTotalPrice;
    }

    public double getItemTotalPriceWithTax() {
	return itemTotalPriceWithTax;
    }

    public double getItemTotaltax() {
	return itemTotaltax;
    }
}
