package com.shopping.client;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopping.exception.PriceClientException;
import com.shopping.exception.ProductNotFoundException;
import com.shopping.validation.CartItemValidator;

public class PriceClient {
    private static final String BASE_URI = "https://equalexperts.github.io/backend-take-home-test-data/";
    private final ObjectMapper objectMapper;
    private final CartItemValidator cartItemValidator;

    public PriceClient() {
	objectMapper = new ObjectMapper();
	cartItemValidator = new CartItemValidator();
    }

    public double getPrice(String productName) {
	cartItemValidator.validateProductName(productName);
	return getProductPrice(productName);
    }

    public double getProductPrice(String productName) {
	double price;
	try {
	    HttpClient client = HttpClient.newHttpClient();
	    HttpRequest request = HttpRequest.newBuilder().uri(URI.create(BASE_URI + productName + ".json")).GET()
		    .build();
	    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
	    if (null != response && response.statusCode() == 200 && null != response.body()) {
		price = getPriceFromResponse(response);
	    } else {
		throw new ProductNotFoundException(productName);
	    }
	} catch (ProductNotFoundException e) {
	    throw e;
	} catch (InterruptedException e) {
	    Thread.currentThread().interrupt();
	    throw new PriceClientException("Thread was interrupted while fetching product price", e);
	} catch (Exception e) {
	    throw new PriceClientException("Error occurred while fetching product price", e);
	}
	return price;
    }

    private double getPriceFromResponse(HttpResponse<String> response) throws JsonProcessingException {
	ProductPrice productPrice = objectMapper.readValue(response.body(), ProductPrice.class);
	return Optional.of(productPrice.getPrice()).orElse(0.0);
    }
}