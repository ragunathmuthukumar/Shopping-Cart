package com.shopping.cart;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

public class PricingService {
    private final ObjectMapper objectMapper;
    private final ProductService productService;
    private final String BASE_URI = "https://equalexperts.github.io/backend-take-home-test-data/";

    public PricingService() {
	objectMapper = new ObjectMapper();
	productService = new ProductService();
    }

    public double getPrices(String productName) {
	productService.validateProductName(productName);
	return getProductPrice(productName);
    }

    public double getProductPrice(String productName) {
	double price = 0.0;
	try (HttpClient client = HttpClient.newHttpClient()) {
	    HttpRequest request = HttpRequest.newBuilder().uri(URI.create(BASE_URI + productName + ".json")).GET()
		    .build();
	    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
	    if (null != response && response.statusCode() == 200 && null != response.body()) {
		ProductPrice productPrice = objectMapper.readValue(response.body(), ProductPrice.class);
		if (null != productPrice) {
		    price = productPrice.getPrice();
		}
	    } else {
		throw new RuntimeException("Error getting price for product " + productName);
	    }
	} catch (Exception e) {
	    throw new RuntimeException(e.getMessage());
	}
	return price;
    }
}