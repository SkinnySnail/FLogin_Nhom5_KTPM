package com.flogin.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.flogin.dto.ProductDto;

@DisplayName("Product Service Unit Tests")
class ProductServiceTest {

    private ProductService productService;

    @BeforeEach
    void setup() {
        productService = new ProductService();
    }

    @Test
    @DisplayName("TC_PRODUCT_BE_01: Tạo sản phẩm mới thành công")
    void testCreateProduct() {
        // Arrange
        ProductDto productDto = new ProductDto();
        productDto.setName("Laptop Dell");
        productDto.setPrice(15000000);
        productDto.setQuantity(10);
        productDto.setCategory("Electronics");
        
        // Act
        ProductDto result = productService.createProduct(productDto);
        
        // Assert
        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals("Laptop Dell", result.getName());
        assertEquals(15000000, result.getPrice());
        assertEquals(10, result.getQuantity());
    }

    @Test
    @DisplayName("TC_PRODUCT_BE_02: Lấy sản phẩm theo ID thành công")
    void testGetProductById() {
        // Arrange
        Long productId = 1L;
        
        // Act
        ProductDto result = productService.getProductById(productId);
        
        // Assert
        assertNotNull(result);
        assertEquals(productId, result.getId());
    }

    @Test
    @DisplayName("TC_PRODUCT_BE_03: Cập nhật sản phẩm thành công")
    void testUpdateProduct() {
        // Arrange
        Long productId = 1L;
        ProductDto updateDto = new ProductDto();
        updateDto.setName("Laptop Dell Updated");
        updateDto.setPrice(14000000);
        updateDto.setQuantity(15);
        
        // Act
        ProductDto result = productService.updateProduct(productId, updateDto);
        
        // Assert
        assertNotNull(result);
        assertEquals("Laptop Dell Updated", result.getName());
        assertEquals(14000000, result.getPrice());
    }

    @Test
    @DisplayName("TC_PRODUCT_BE_04: Xóa sản phẩm thành công")
    void testDeleteProduct() {
        // Arrange
        Long productId = 1L;
        
        // Act
        boolean result = productService.deleteProduct(productId);
        
        // Assert
        assertTrue(result);
    }
}
