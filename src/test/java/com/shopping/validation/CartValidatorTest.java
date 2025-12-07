package com.shopping.validation;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.shopping.exception.InvalidCartItemException;

class CartValidatorTest {
    private final CartItemValidator validator = new CartItemValidator();

    @Test
    void validateCartItem_validNameAndQuantity() {
	assertDoesNotThrow(() -> validator.validateCartItem("cheerios", 1));
    }

    @Test
    void validateProductName_validName() {
	assertDoesNotThrow(() -> validator.validateProductName("cornflakes"));
    }

    @Test
    void validateQuality_validQuantity() {
	assertDoesNotThrow(() -> validator.validateQuality("cornflakes", 2));
    }

    @Test
    void validateCartItem_with_supported_specialChar_shouldPass() {
	assertDoesNotThrow(() -> validator.validateQuality("corn_flakes", 1));
	assertDoesNotThrow(() -> validator.validateQuality("corn-flakes", 1));
    }

    // Negative test cases
    @Test
    void validateCartItem_emptyName_shouldThrow() {
	InvalidCartItemException ex = assertThrows(InvalidCartItemException.class,
		() -> validator.validateCartItem("", 1));
	assertEquals("CartItem name cannot be empty", ex.getMessage());
    }

    @Test
    void validateCartItem_nullName_shouldThrow() {
	InvalidCartItemException ex = assertThrows(InvalidCartItemException.class,
		() -> validator.validateCartItem(null, 1));
	assertEquals("CartItem name cannot be empty", ex.getMessage());
    }

    @Test
    void validateCartItem_forbiddenChars_shouldThrow() {
	InvalidCartItemException ex = assertThrows(InvalidCartItemException.class,
		() -> validator.validateCartItem("cornflakes/2%", 1));
	assertEquals("CartItem name contains forbidden characters", ex.getMessage());
    }

    @Test
    void validateCartItem_zeroQuantity_shouldThrow() {
	InvalidCartItemException ex = assertThrows(InvalidCartItemException.class,
		() -> validator.validateCartItem("cornflakes", 0));
	assertEquals("cornflakes is not a valid cart item.Quantity must be greater than zero", ex.getMessage());
    }

    @Test
    void validateCartItem_negativeQuantity_shouldThrow() {
	InvalidCartItemException ex = assertThrows(InvalidCartItemException.class,
		() -> validator.validateCartItem("cornflakes", -5));
	assertEquals("cornflakes is not a valid cart item.Quantity must be greater than zero", ex.getMessage());
    }
}