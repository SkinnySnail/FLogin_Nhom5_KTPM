package com.flogin.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Optional;

import com.flogin.dto.ProductDto;

import com.flogin.entity.Product;
import com.flogin.repository.ProductRepository;

@DisplayName("Product Service Unit Tests")
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
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

        Product savedEntity = new Product();
        savedEntity.setId(1L);
        savedEntity.setName("Laptop Dell");
        savedEntity.setPrice(15000000);
        savedEntity.setQuantity(10);
        savedEntity.setCategory("Electronics");

        when(productRepository.save(any(Product.class))).thenReturn(savedEntity);

        // Act
        ProductDto result = productService.createProduct(productDto);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Laptop Dell", result.getName());
        assertEquals(15000000, result.getPrice());
        assertEquals(10, result.getQuantity());
    }

    @Test
    @DisplayName("TC_PRODUCT_BE_02: Lấy sản phẩm theo ID thành công")
    void testGetProductById() {
        // Arrange
        Long productId = 1L;
        Product entity = new Product();
        entity.setId(productId);
        entity.setName("Laptop Dell");
        entity.setPrice(15000000);
        entity.setQuantity(10);
        entity.setCategory("Electronics");
        when(productRepository.findById(productId)).thenReturn(Optional.of(entity));

        // Act
        ProductDto result = productService.getProductById(productId);

        // Assert
        assertNotNull(result);
        assertEquals(productId, result.getId());
        assertEquals("Laptop Dell", result.getName());
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

        Product entity = new Product();
        entity.setId(productId);
        entity.setName("Laptop Dell");
        entity.setPrice(15000000);
        entity.setQuantity(10);
        entity.setCategory("Electronics");

        Product updatedEntity = new Product();
        updatedEntity.setId(productId);
        updatedEntity.setName("Laptop Dell Updated");
        updatedEntity.setPrice(14000000);
        updatedEntity.setQuantity(15);
        updatedEntity.setCategory("Electronics");

        when(productRepository.findById(productId)).thenReturn(Optional.of(entity));
        when(productRepository.save(any(Product.class))).thenReturn(updatedEntity);

        // Act
        ProductDto result = productService.updateProduct(productId, updateDto);

        // Assert
        assertNotNull(result);
        assertEquals("Laptop Dell Updated", result.getName());
        assertEquals(14000000, result.getPrice());
        assertEquals(15, result.getQuantity());
    }

    @Test
    @DisplayName("TC_PRODUCT_BE_04: Xóa sản phẩm thành công")
    void testDeleteProduct() {
        // Arrange
        Long productId = 1L;
        when(productRepository.existsById(productId)).thenReturn(true);
        // Act
        boolean result = productService.deleteProduct(productId);
        // Assert
        assertTrue(result);
        verify(productRepository).deleteById(productId);
    }

    @Test
    @DisplayName("TC_PRODUCT_BE_05: Lấy sản phẩm không tồn tại")
    void testGetProductById_NotFound() {
        // Arrange
        Long productId = 999L;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // Act
        ProductDto result = productService.getProductById(productId);

        // Assert
        assertNull(result, "Kết quả phải là null khi không tìm thấy sản phẩm");
    }

    @Test
    @DisplayName("TC_PRODUCT_BE_06: Xóa sản phẩm không tồn tại")
    void testDeleteProduct_NotFound() {
        // Arrange
        Long productId = 999L;
        when(productRepository.existsById(productId)).thenReturn(false);

        // Act
        boolean result = productService.deleteProduct(productId);

        // Assert
        assertFalse(result, "Kết quả phải là false khi không tìm thấy sản phẩm để xóa");
        verify(productRepository, never()).deleteById(productId);
    }
}
