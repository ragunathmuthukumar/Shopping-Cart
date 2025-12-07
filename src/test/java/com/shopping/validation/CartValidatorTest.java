package com.shopping.validation;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.shopping.exception.InvalidCartItemException;

class CartValidatorTest {
    private final CartItemValidator validator = new CartItemValidator();

    @Test
    void validateProductName_validName() {
	assertDoesNotThrow(() -> validator.validateProductName("cornflakes"));
    }

    @Test
    void validateQuality_validQuantity() {
	assertDoesNotThrow(() -> validator.validateQuantity("cornflakes", 2));
    }

    @Test
    void validateCartItem_with_supported_specialChar_shouldPass() {
	assertDoesNotThrow(() -> validator.validateQuantity("corn_flakes", 1));
	assertDoesNotThrow(() -> validator.validateQuantity("corn-flakes", 1));
    }

    // Negative test cases
    @Test
    void validateCartItem_emptyName_shouldThrow() {
	InvalidCartItemException ex = assertThrows(InvalidCartItemException.class,
		() -> validator.validateProductName(""));
	assertEquals("CartItem name cannot be empty", ex.getMessage());
    }

    @Test
    void validateCartItem_nullName_shouldThrow() {
	InvalidCartItemException ex = assertThrows(InvalidCartItemException.class,
		() -> validator.validateProductName(null));
	assertEquals("CartItem name cannot be empty", ex.getMessage());
    }

    @Test
    void validateCartItem_forbiddenChars_shouldThrow() {
	InvalidCartItemException ex = assertThrows(InvalidCartItemException.class,
		() -> validator.validateProductName("cornflakes/2%"));
	assertEquals("CartItem name contains forbidden characters", ex.getMessage());
    }

    @Test
    void validateCartItem_zeroQuantity_shouldThrow() {
	InvalidCartItemException ex = assertThrows(InvalidCartItemException.class,
		() -> validator.validateQuantity("cornflakes", 0));
	assertEquals("cornflakes is not a valid cart item.Quantity must be greater than zero", ex.getMessage());
    }

    @Test
    void validateCartItem_negativeQuantity_shouldThrow() {
	InvalidCartItemException ex = assertThrows(InvalidCartItemException.class,
		() -> validator.validateQuantity("cornflakes", -5));
	assertEquals("cornflakes is not a valid cart item.Quantity must be greater than zero", ex.getMessage());
    }
}