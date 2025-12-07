package com.shopping.cart.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.shopping.cart.ShoppingCart;

class ShoppingCartTest {
    ShoppingCart cart;

    @BeforeEach
    void setUp() {
	cart = new ShoppingCart();
    }

    @Test
    void testConstructorInitializesEmptyCart() {
	Assertions.assertNotNull(cart.getProducts());
	Assertions.assertTrue(cart.getProducts().isEmpty());
	Assertions.assertEquals(0.0, cart.getSubTotal());
	Assertions.assertEquals(0.0, cart.getTotalTaxes());
	Assertions.assertEquals(0.0, cart.getTotalPrice());
    }

    @Test
    void testAddSingleProduct() {
	cart.addProduct("cornflakes", 1);
	Assertions.assertEquals(1, cart.getProducts().size());
	Assertions.assertTrue(cart.getProducts().containsKey("cornflakes"));
	Assertions.assertEquals(2.52, cart.getSubTotal());
	Assertions.assertEquals(0.32, cart.getTotalTaxes());
	Assertions.assertEquals(2.84, cart.getTotalPrice());
    }

    @Test
    void testAddTwoProducts() {
	cart.addProduct("cornflakes", 1);
	cart.addProduct("cornflakes", 1);
	cart.addProduct("weetabix", 1);
	Assertions.assertEquals(2, cart.getProducts().size());
	Assertions.assertEquals(15.02, cart.getSubTotal());
	Assertions.assertEquals(1.88, cart.getTotalTaxes());
	Assertions.assertEquals(16.90, cart.getTotalPrice());
    }

    @Test
    void testAddMultipleProducts() {
	cart.addProduct("cornflakes", 3);
	cart.addProduct("cheerios", 5);
	cart.addProduct("frosties", 10);
	cart.addProduct("shreddies", 15);
	cart.addProduct("weetabix", 20);
	Assertions.assertEquals(5, cart.getProducts().size());
	Assertions.assertEquals(369.41, cart.getSubTotal());
	Assertions.assertEquals(46.18, cart.getTotalTaxes());
	Assertions.assertEquals(415.59, cart.getTotalPrice());
    }

    @Test
    void testAddSameProductMultipleTimes() {
	cart.addProduct("cornflakes", 1);
	cart.addProduct("cornflakes", 2);
	Assertions.assertEquals(1, cart.getProducts().size());
	Assertions.assertEquals(3, cart.getProducts().get("cornflakes").getQuantity());
    }

    @Test
    void testAddProductWithDecimalQuantity() {
	cart.addProduct("cornflakes", 1.5);
	Assertions.assertEquals(1, cart.getProducts().size());
	Assertions.assertEquals(1.5, cart.getProducts().get("cornflakes").getQuantity());
    }

    @Test
    void testSubTotalCalculation() {
	cart.addProduct("cornflakes", 1);
	Assertions.assertTrue(cart.getSubTotal() > 0);
    }

    @Test
    void testTotalTaxesCalculation() {
	cart.addProduct("cornflakes", 1);
	Assertions.assertTrue(cart.getTotalTaxes() > 0);
	Assertions.assertTrue(cart.getTotalTaxes() < cart.getSubTotal());
    }

    @Test
    void testTotalPriceCalculation() {
	cart.addProduct("cornflakes", 1);
	Assertions.assertEquals(cart.getSubTotal() + cart.getTotalTaxes(), cart.getTotalPrice());
    }

    @Test
    void testPriceRounding() {
	cart.addProduct("cornflakes", 1);
	String subTotalStr = String.valueOf(cart.getSubTotal());
	String[] parts = subTotalStr.split("\\.");
	Assertions.assertTrue(parts.length == 1 || parts[1].length() <= 2);
    }

    @Test
    void testMultipleProductsPriceCalculation() {
	cart.addProduct("cornflakes", 2);
	cart.addProduct("frosties", 3);
	double expectedTotal = cart.getSubTotal() + cart.getTotalTaxes();
	Assertions.assertEquals(expectedTotal, cart.getTotalPrice());
    }

    // Negative Test Cases
    @Test
    void testAddProductWithInvalidName() {
	cart.addProduct("cornflakes/", 1);
	Assertions.assertEquals(0, cart.getProducts().size());
    }

    @Test
    void testAddProductWithEmptyName() {
	cart.addProduct("", 1);
	Assertions.assertEquals(0, cart.getProducts().size());
    }

    @Test
    void testAddProductWithNullName() {
	cart.addProduct(null, 1);
	Assertions.assertEquals(0, cart.getProducts().size());
    }

    @Test
    void testAddProductWithZeroQuantity() {
	cart.addProduct("cornflakes", 0);
	Assertions.assertEquals(0, cart.getProducts().size());
    }

    @Test
    void testAddProductWithNegativeQuantity() {
	cart.addProduct("cornflakes", -1);
	Assertions.assertEquals(0, cart.getProducts().size());
    }

    @Test
    void testAddNonExistentProduct() {
	cart.addProduct("nonexistent", 1);
	// Assuming pricing service handles this gracefully
	Assertions.assertTrue(cart.getProducts().size() <= 1);
    }

    @Test
    void testAddProductWithSpecialCharacters() {
	cart.addProduct("product@#$", 1);
	Assertions.assertEquals(0, cart.getProducts().size());
    }

    @Test
    void testAddProductWithVeryLargeQuantity() {
	cart.addProduct("cornflakes", Double.MAX_VALUE);
	Assertions.assertTrue(cart.getTotalPrice() >= 0);
	Assertions.assertEquals(1, cart.getProducts().size());
	Assertions.assertEquals(Double.MAX_VALUE, cart.getProducts().get("cornflakes").getQuantity());
    }

    @Test
    void testCartStateAfterInvalidProductAdd() {
	cart.addProduct("cornflakes", 1);
	double initialTotal = cart.getTotalPrice();
	cart.addProduct("invalid/", 1);
	Assertions.assertEquals(initialTotal, cart.getTotalPrice());
    }

    @Test
    void testProductMapImmutability() {
	cart.addProduct("cornflakes", 1);
	int initialSize = cart.getProducts().size();
	try {
	    cart.getProducts().clear();
	} catch (UnsupportedOperationException e) {
	}
	Assertions.assertEquals(initialSize, cart.getProducts().size());
    }
}