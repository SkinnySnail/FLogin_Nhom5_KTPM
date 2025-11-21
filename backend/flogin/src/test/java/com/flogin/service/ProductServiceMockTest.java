package com.flogin.service;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import com.flogin.dto.ProductDto;
import com.flogin.entity.Product;
import com.flogin.repository.ProductRepository;
import com.flogin.service.ProductService;

class ProductServiceMockTest {
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    public ProductServiceMockTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetProductById() {
        Product mockProduct = new Product(1L, "Laptop", 15000000.0, 10, "Electronics");
        when(productRepository.findById(1L)).thenReturn(Optional.of(mockProduct));

        ProductDto result = productService.getProductById(1L);

        assertNotNull(result);
        assertEquals("Laptop", result.getName());
        verify(productRepository).findById(1L);
    }
}
