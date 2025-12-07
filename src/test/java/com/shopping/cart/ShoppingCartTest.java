package com.shopping.cart;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.shopping.exception.InvalidCartItemException;
import com.shopping.exception.ProductNotFoundException;

class ShoppingCartTest {
    ShoppingCart cart;

    @BeforeEach
    void setUp() {
	cart = new ShoppingCart();
    }

    // Cart Item related tests
    @Test
    void testConstructorInitializesEmptyCart() {
	assertNotNull(cart.getProducts());
	assertTrue(cart.getProducts().isEmpty());
	assertEquals(0.0, cart.getSubTotal());
	assertEquals(0.0, cart.getTotalTaxes());
	assertEquals(0.0, cart.getTotalPrice());
    }

    @Test
    void testAddAvailableProduct() {
	cart.addProduct("cornflakes", 1);
	assertEquals(1, cart.getProducts().size());
	assertTrue(cart.getProducts().containsKey("cornflakes"));
	assertEquals(2.52, cart.getSubTotal());
	assertEquals(0.32, cart.getTotalTaxes());
	assertEquals(2.84, cart.getTotalPrice());
    }

    @Test
    void testAddTwoAvailableProducts() {
	cart.addProduct("cornflakes", 1);
	cart.addProduct("cornflakes", 1);
	cart.addProduct("weetabix", 1);
	assertEquals(2, cart.getProducts().size());
	assertEquals(15.02, cart.getSubTotal());
	assertEquals(1.88, cart.getTotalTaxes());
	assertEquals(16.90, cart.getTotalPrice());
    }

    @Test
    void testAddMultipleProducts() {
	cart.addProduct("cornflakes", 3);
	cart.addProduct("cheerios", 5);
	cart.addProduct("frosties", 10);
	cart.addProduct("shreddies", 15);
	cart.addProduct("weetabix", 20);
	assertEquals(5, cart.getProducts().size());
	assertEquals(369.41, cart.getSubTotal());
	assertEquals(46.18, cart.getTotalTaxes());
	assertEquals(415.59, cart.getTotalPrice());
    }

    @Test
    void testAddSameProductMultipleTimes() {
	cart.addProduct("cornflakes", 1);
	cart.addProduct("cornflakes", 2);
	assertEquals(1, cart.getProducts().size());
	assertEquals(3, cart.getProducts().get("cornflakes").getQuantity());
    }

    @Test
    void testMultipleProductsPriceCalculation() {
	cart.addProduct("cornflakes", 2);
	cart.addProduct("frosties", 3);
	double expectedTotal = cart.getSubTotal() + cart.getTotalTaxes();
	assertEquals(expectedTotal, cart.getTotalPrice());
    }

    @Test
    void testAddNonExistentProduct() {
	assertThrows(ProductNotFoundException.class, () -> cart.addProduct("nonexistent", 1));
	assertTrue(cart.getProducts().size() <= 1);
    }

    @Test
    void testAddProductWithInvalidName() {
	assertThrows(InvalidCartItemException.class, () -> cart.addProduct("cornflakes/", 1));
	assertEquals(0, cart.getProducts().size());
    }

    @Test
    void testAddProductWithEmptyName() {
	assertThrows(InvalidCartItemException.class, () -> cart.addProduct("", 1));
	assertEquals(0, cart.getProducts().size());
    }

    @Test
    void testAddProductWithNullName() {
	assertThrows(InvalidCartItemException.class, () -> cart.addProduct(null, 1));
	assertEquals(0, cart.getProducts().size());
    }

    @Test
    void testAddProductWithZeroQuantity() {
	assertThrows(InvalidCartItemException.class, () -> cart.addProduct("cornflakes", 0));
	assertEquals(0, cart.getProducts().size());
    }

    @Test
    void testAddProductWithNegativeQuantity() {
	assertThrows(InvalidCartItemException.class, () -> cart.addProduct("cornflakes", -1));
	assertEquals(0, cart.getProducts().size());
    }

    @Test
    void testAddProductWithSpecialCharacters() {
	assertThrows(InvalidCartItemException.class, () -> cart.addProduct("product@#$", 1));
	assertEquals(0, cart.getProducts().size());
    }

    @Test
    void testAddProductWithVeryLargeQuantity() {
	cart.addProduct("cornflakes", Integer.MAX_VALUE);
	assertTrue(cart.getTotalPrice() >= 0);
	assertEquals(1, cart.getProducts().size());
	assertEquals(Integer.MAX_VALUE, cart.getProducts().get("cornflakes").getQuantity());
    }

    @Test
    void testCartStateAfterInvalidProductAdd() {
	cart.addProduct("cornflakes", 1);
	double initialTotal = cart.getTotalPrice();
	assertThrows(InvalidCartItemException.class, () -> cart.addProduct("invalid/", 1));
	assertEquals(initialTotal, cart.getTotalPrice());
    }

    @Test
    void testProductMapImmutability() {
	cart.addProduct("cornflakes", 1);
	int initialSize = cart.getProducts().size();
	try {
	    cart.getProducts().clear();
	} catch (UnsupportedOperationException e) {
	}
	assertEquals(initialSize, cart.getProducts().size());
    }

//	Calculation related tests

    @Test
    void testSubTotalCalculation() {
	cart.addProduct("cornflakes", 1);
	assertTrue(cart.getSubTotal() > 0);
    }

    @Test
    void testTotalTaxesCalculation() {
	cart.addProduct("cornflakes", 1);
	assertTrue(cart.getTotalTaxes() > 0);
	assertTrue(cart.getTotalTaxes() < cart.getSubTotal());
    }

    @Test
    void testTotalPriceCalculation() {
	cart.addProduct("cornflakes", 1);
	assertTrue(cart.getTotalPrice() > 0);
	assertEquals(cart.getSubTotal() + cart.getTotalTaxes(), cart.getTotalPrice());
    }

    @Test
    void testPriceRounding() {
	cart.addProduct("cornflakes", 1);
	String subTotalStr = String.valueOf(cart.getSubTotal());
	String[] parts = subTotalStr.split("\\.");
	assertTrue(parts.length == 1 || parts[1].length() <= 2);
    }
}