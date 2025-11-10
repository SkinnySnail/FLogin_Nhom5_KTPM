package com.flogin.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Validation Utility Tests")
class ValidationUtilTest {
    
    // ===== USERNAME VALIDATION TESTS =====
    
    @Test
    @DisplayName("TC_LOGIN_BE_04: Username rỗng - phải trả lỗi")
    void testUsernameEmpty() {
        String error = ValidationUtil.validateUsername("");
        assertEquals("Tên đăng nhập không được để trống", error);
    }
    
    @Test
    @DisplayName("TC_LOGIN_BE_05: Username quá ngắn (< 3 ký tự) - phải trả lỗi")
    void testUsernameToShort() {
        String error = ValidationUtil.validateUsername("ab");
        assertEquals("Tên đăng nhập phải có ít nhất 3 ký tự", error);
    }
    
    @Test
    @DisplayName("TC_LOGIN_BE_06: Username quá dài (> 50 ký tự) - phải trả lỗi")
    void testUsernameTooLong() {
        String username = "a".repeat(51);
        String error = ValidationUtil.validateUsername(username);
        assertEquals("Tên đăng nhập không được vượt quá 50 ký tự", error);
    }
    
    @Test
    @DisplayName("TC_LOGIN_BE_07: Username chứa ký tự đặc biệt không hợp lệ - phải trả lỗi")
    void testUsernameSpecialCharacters() {
        String error = ValidationUtil.validateUsername("user@name");
        assertEquals("Tên đăng nhập chỉ chứa a-z, A-Z, 0-9, -, ., _", error);
    }
    
    @Test
    @DisplayName("TC_LOGIN_BE_08: Username hợp lệ (3 ký tự) - không có lỗi")
    void testUsernameValidMinLength() {
        String error = ValidationUtil.validateUsername("abc");
        assertEquals("", error);
    }
    
    @Test
    @DisplayName("TC_LOGIN_BE_09: Username hợp lệ với số và ký tự đặc biệt hợp lệ - không có lỗi")
    void testUsernameValidFormat() {
        String error = ValidationUtil.validateUsername("user_123.test-name");
        assertEquals("", error);
    }
    
    @Test
    @DisplayName("TC_LOGIN_BE_10: Username = null - phải trả lỗi")
    void testUsernameNull() {
        String error = ValidationUtil.validateUsername(null);
        assertEquals("Tên đăng nhập không được để trống", error);
    }
    
    // ===== PASSWORD VALIDATION TESTS =====
    
    @Test
    @DisplayName("TC_LOGIN_BE_11: Password rỗng - phải trả lỗi")
    void testPasswordEmpty() {
        String error = ValidationUtil.validatePassword("");
        assertEquals("Mật khẩu không được để trống", error);
    }
    
    @Test
    @DisplayName("TC_LOGIN_BE_12: Password quá ngắn (< 6 ký tự) - phải trả lỗi")
    void testPasswordTooShort() {
        String error = ValidationUtil.validatePassword("Test1");
        assertEquals("Mật khẩu phải có ít nhất 6 ký tự", error);
    }
    
    @Test
    @DisplayName("TC_LOGIN_BE_13: Password quá dài (> 100 ký tự) - phải trả lỗi")
    void testPasswordTooLong() {
        String password = "Test123456" + "a".repeat(95);
        String error = ValidationUtil.validatePassword(password);
        assertEquals("Mật khẩu không được vượt quá 100 ký tự", error);
    }
    
    @Test
    @DisplayName("TC_LOGIN_BE_14: Password không chứa chữ - phải trả lỗi")
    void testPasswordNoLetter() {
        String error = ValidationUtil.validatePassword("123456");
        assertEquals("Mật khẩu phải chứa ít nhất một chữ cái", error);
    }
    
    @Test
    @DisplayName("TC_LOGIN_BE_15: Password không chứa số - phải trả lỗi")
    void testPasswordNoNumber() {
        String error = ValidationUtil.validatePassword("TestPassword");
        assertEquals("Mật khẩu phải chứa ít nhất một chữ số", error);
    }
    
    @Test
    @DisplayName("TC_LOGIN_BE_16: Password hợp lệ (6 ký tự, chữ + số) - không có lỗi")
    void testPasswordValidMinLength() {
        String error = ValidationUtil.validatePassword("Test12");
        assertEquals("", error);
    }
    
    @Test
    @DisplayName("TC_LOGIN_BE_17: Password hợp lệ (chữ hoa, chữ thường, số) - không có lỗi")
    void testPasswordValidFormat() {
        String error = ValidationUtil.validatePassword("TestPassword123");
        assertEquals("", error);
    }
    
    @Test
    @DisplayName("TC_LOGIN_BE_18: Password = null - phải trả lỗi")
    void testPasswordNull() {
        String error = ValidationUtil.validatePassword(null);
        assertEquals("Mật khẩu không được để trống", error);
    }
    
    // ===== PRODUCT NAME VALIDATION TESTS =====
    
    @Test
    @DisplayName("TC_PRODUCT_BE_05: Product name rỗng - phải trả lỗi")
    void testProductNameEmpty() {
        String error = ValidationUtil.validateProductName("");
        assertEquals("Tên sản phẩm không được để trống", error);
    }
    
    @Test
    @DisplayName("TC_PRODUCT_BE_06: Product name quá ngắn (< 3 ký tự) - phải trả lỗi")
    void testProductNameTooShort() {
        String error = ValidationUtil.validateProductName("ab");
        assertEquals("Tên sản phẩm phải có ít nhất 3 ký tự", error);
    }
    
    @Test
    @DisplayName("TC_PRODUCT_BE_07: Product name quá dài (> 100 ký tự) - phải trả lỗi")
    void testProductNameTooLong() {
        String name = "a".repeat(101);
        String error = ValidationUtil.validateProductName(name);
        assertEquals("Tên sản phẩm không được vượt quá 100 ký tự", error);
    }
    
    @Test
    @DisplayName("TC_PRODUCT_BE_08: Product name hợp lệ - không có lỗi")
    void testProductNameValid() {
        String error = ValidationUtil.validateProductName("Laptop Dell XPS 13");
        assertEquals("", error);
    }
    
    // ===== PRODUCT PRICE VALIDATION TESTS =====
    
    @Test
    @DisplayName("TC_PRODUCT_BE_09: Price = 0 - phải trả lỗi")
    void testProductPriceZero() {
        String error = ValidationUtil.validateProductPrice(0);
        assertEquals("Giá sản phẩm phải lớn hơn 0", error);
    }
    
    @Test
    @DisplayName("TC_PRODUCT_BE_10: Price âm - phải trả lỗi")
    void testProductPriceNegative() {
        String error = ValidationUtil.validateProductPrice(-1000);
        assertEquals("Giá sản phẩm phải lớn hơn 0", error);
    }
    
    @Test
    @DisplayName("TC_PRODUCT_BE_11: Price vượt quá max (> 999,999,999) - phải trả lỗi")
    void testProductPriceTooHigh() {
        String error = ValidationUtil.validateProductPrice(1000000000L);
        assertEquals("Giá sản phẩm không được vượt quá 999,999,999", error);
    }
    
    @Test
    @DisplayName("TC_PRODUCT_BE_12: Price hợp lệ (1) - không có lỗi")
    void testProductPriceValidMin() {
        String error = ValidationUtil.validateProductPrice(1);
        assertEquals("", error);
    }
    
    @Test
    @DisplayName("TC_PRODUCT_BE_13: Price hợp lệ (999,999,999) - không có lỗi")
    void testProductPriceValidMax() {
        String error = ValidationUtil.validateProductPrice(999999999L);
        assertEquals("", error);
    }
    
    @Test
    @DisplayName("TC_PRODUCT_BE_14: Price hợp lệ (15,000,000) - không có lỗi")
    void testProductPriceValid() {
        String error = ValidationUtil.validateProductPrice(15000000);
        assertEquals("", error);
    }
    
    // ===== PRODUCT QUANTITY VALIDATION TESTS =====
    
    @Test
    @DisplayName("TC_PRODUCT_BE_15: Quantity âm - phải trả lỗi")
    void testProductQuantityNegative() {
        String error = ValidationUtil.validateProductQuantity(-1);
        assertEquals("Số lượng sản phẩm không được âm", error);
    }
    
    @Test
    @DisplayName("TC_PRODUCT_BE_16: Quantity vượt quá max (> 99,999) - phải trả lỗi")
    void testProductQuantityTooHigh() {
        String error = ValidationUtil.validateProductQuantity(100000);
        assertEquals("Số lượng sản phẩm không được vượt quá 99,999", error);
    }
    
    @Test
    @DisplayName("TC_PRODUCT_BE_17: Quantity = 0 - không có lỗi")
    void testProductQuantityZero() {
        String error = ValidationUtil.validateProductQuantity(0);
        assertEquals("", error);
    }
    
    @Test
    @DisplayName("TC_PRODUCT_BE_18: Quantity = 99,999 - không có lỗi")
    void testProductQuantityValidMax() {
        String error = ValidationUtil.validateProductQuantity(99999);
        assertEquals("", error);
    }
    
    @Test
    @DisplayName("TC_PRODUCT_BE_19: Quantity hợp lệ (10) - không có lỗi")
    void testProductQuantityValid() {
        String error = ValidationUtil.validateProductQuantity(10);
        assertEquals("", error);
    }
    
    // ===== PRODUCT DESCRIPTION VALIDATION TESTS =====
    
    @Test
    @DisplayName("TC_PRODUCT_BE_20: Description rỗng - không có lỗi")
    void testProductDescriptionEmpty() {
        String error = ValidationUtil.validateProductDescription("");
        assertEquals("", error);
    }
    
    @Test
    @DisplayName("TC_PRODUCT_BE_21: Description quá dài (> 500 ký tự) - phải trả lỗi")
    void testProductDescriptionTooLong() {
        String description = "a".repeat(501);
        String error = ValidationUtil.validateProductDescription(description);
        assertEquals("Mô tả sản phẩm không được vượt quá 500 ký tự", error);
    }
    
    @Test
    @DisplayName("TC_PRODUCT_BE_22: Description = 500 ký tự - không có lỗi")
    void testProductDescriptionValidMax() {
        String description = "a".repeat(500);
        String error = ValidationUtil.validateProductDescription(description);
        assertEquals("", error);
    }
    
    @Test
    @DisplayName("TC_PRODUCT_BE_23: Description hợp lệ - không có lỗi")
    void testProductDescriptionValid() {
        String error = ValidationUtil.validateProductDescription("This is a valid product description");
        assertEquals("", error);
    }
}
