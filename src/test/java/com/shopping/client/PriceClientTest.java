package com.shopping.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.shopping.exception.InvalidCartItemException;
import com.shopping.exception.ProductNotFoundException;

class PriceClientTest {
    PriceClient priceClient = new PriceClient();

    @Test
    void testGetPrice_Cheerios() {
	assertEquals(8.43, priceClient.getPrice("cheerios"));
    }

    @Test
    void testGetPrice_Cornflakes() {
	assertEquals(2.52, priceClient.getPrice("cornflakes"));
    }

    @Test
    void testGetPrice_ProductNotFound() {
	assertThrows(ProductNotFoundException.class, () -> priceClient.getPrice("unknownproduct"));
    }

    @Test
    void testGetPrice_NullProductName() {
	assertThrows(InvalidCartItemException.class, () -> priceClient.getPrice(null));
    }

    @Test
    void testGetPrice_EmptyProductName() {
	assertThrows(InvalidCartItemException.class, () -> priceClient.getPrice(""));
    }

    @Test
    void testGetPrice_ForbiddenCharacters() {
	assertThrows(InvalidCartItemException.class, () -> priceClient.getPrice("cheerios/"));
	assertThrows(InvalidCartItemException.class, () -> priceClient.getPrice("corn:flakes"));
	assertThrows(InvalidCartItemException.class, () -> priceClient.getPrice("corn*flakes"));
    }
}