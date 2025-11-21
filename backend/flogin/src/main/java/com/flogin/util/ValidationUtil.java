package com.flogin.util;

public class ValidationUtil {
    // Regex patterns
    private static final String USERNAME_PATTERN = "^[a-zA-Z0-9._-]{3,50}$";
    private static final String PASSWORD_PATTERN = "^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z0-9]{6,100}$";

    /**
     * Validate username
     * @param username username to validate
     * @return error message or empty string if valid
     */
    public static String validateUsername(String username) {
        if (username == null || username.isEmpty()) {
            return "Tên đăng nhập không được để trống";
        }
        if (username.length() < 3) {
            return "Tên đăng nhập phải có ít nhất 3 ký tự";
        }
        if (username.length() > 50) {
            return "Tên đăng nhập không được vượt quá 50 ký tự";
        }
        if (!username.matches(USERNAME_PATTERN)) {
            return "Tên đăng nhập chỉ chứa a-z, A-Z, 0-9, -, ., _";
        }
        return "";
    }

    /**
     * Validate password
     * @param password password to validate
     * @return error message or empty string if valid
     */
    public static String validatePassword(String password) {
        if (password == null || password.isEmpty()) {
            return "Mật khẩu không được để trống";
        }
        if (password.length() < 6) {
            return "Mật khẩu phải có ít nhất 6 ký tự";
        }
        if (password.length() > 100) {
            return "Mật khẩu không được vượt quá 100 ký tự";
        }
        if (!password.matches(".*[a-zA-Z].*")) {
            return "Mật khẩu phải chứa ít nhất một chữ cái";
        }
        if (!password.matches(".*\\d.*")) {
            return "Mật khẩu phải chứa ít nhất một chữ số";
        }
        if (!password.matches(PASSWORD_PATTERN)) {
            return "Mật khẩu không hợp lệ";
        }
        return "";
    }

    /**
     * Validate product name
     * @param name product name to validate
     * @return error message or empty string if valid
     */
    public static String validateProductName(String name) {
        if (name == null || name.isEmpty()) {
            return "Tên sản phẩm không được để trống";
        }
        if (name.length() < 3) {
            return "Tên sản phẩm phải có ít nhất 3 ký tự";
        }
        if (name.length() > 100) {
            return "Tên sản phẩm không được vượt quá 100 ký tự";
        }
        return "";
    }

    /**
     * Validate product price
     * @param price product price to validate
     * @return error message or empty string if valid
     */
    public static String validateProductPrice(long price) {
        if (price <= 0) {
            return "Giá sản phẩm phải lớn hơn 0";
        }
        if (price > 999999999L) {
            return "Giá sản phẩm không được vượt quá 999,999,999";
        }
        return "";
    }

    /**
     * Validate product quantity
     * @param quantity product quantity to validate
     * @return error message or empty string if valid
     */
    public static String validateProductQuantity(int quantity) {
        if (quantity < 0) {
            return "Số lượng sản phẩm không được âm";
        }
        if (quantity > 99999) {
            return "Số lượng sản phẩm không được vượt quá 99,999";
        }
        return "";
    }

    /**
     * Validate product description
     * @param description product description to validate
     * @return error message or empty string if valid
     */
    public static String validateProductDescription(String description) {
        if (description == null || description.isEmpty()) {
            return "";
        }
        if (description.length() > 500) {
            return "Mô tả sản phẩm không được vượt quá 500 ký tự";
        }
        return "";
    }
}
