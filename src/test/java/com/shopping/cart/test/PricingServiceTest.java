package com.shopping.cart.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.shopping.cart.PricingService;

class PricingServiceTest {
    PricingService pricingService = new PricingService();

    @Test
    void testPricingService() {
	Assertions.assertEquals(8.43, pricingService.getPrices("cheerios"));
	Assertions.assertEquals(2.52, pricingService.getPrices("cornflakes"));
	Assertions.assertNotEquals(8.43, pricingService.getPrices("cornflakes"));
    }

    @Test
    void testProductNotAvailable() {
	// Product Price Details Not available in pricing API
	Assertions.assertThrows(RuntimeException.class, () -> pricingService.getPrices("abcd"));
    }

    @Test
    void testInvalidProductNames() {
	// Product Name as empty
	Assertions.assertThrows(RuntimeException.class, () -> pricingService.getPrices(""));
	// Product Name as null
	Assertions.assertThrows(RuntimeException.class, () -> pricingService.getPrices(null));
	// Product Name contains invalid chars
	Assertions.assertThrows(RuntimeException.class, () -> pricingService.getPrices("cheerios/"));
    }
}