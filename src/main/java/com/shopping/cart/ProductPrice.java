package com.shopping.cart;

public class ProductPrice {
    private String title;
    private double price;

    public ProductPrice() {
    }

    public ProductPrice(String title, double price) {
	this.title = title;
	this.price = price;
    }

    public String getTitle() {
	return title;
    }

    public void setTitle(String title) {
	this.title = title;
    }

    public double getPrice() {
	return price;
    }

    public void setPrice(double price) {
	this.price = price;
    }

    @Override
    public String toString() {
	return "ProductPrice{" + "title='" + title + '\'' + ", price=" + price + '}';
    }
}
