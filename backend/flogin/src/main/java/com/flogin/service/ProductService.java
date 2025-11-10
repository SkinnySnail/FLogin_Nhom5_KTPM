package com.flogin.service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import com.flogin.dto.ProductDto;

@Service
public class ProductService {
    
    // Simulate database with HashMap
    private static final Map<Long, ProductDto> productDatabase = new HashMap<>();
    private static final AtomicLong idCounter = new AtomicLong(1L);
    
    // Pre-populate with sample data
    static {
        ProductDto sampleProduct = new ProductDto("Laptop Dell", 15000000, 10, "Electronics");
        sampleProduct.setId(1L);
        productDatabase.put(1L, sampleProduct);
    }

    /**
     * Create a new product
     * @param productDto product data to create
     * @return created product with ID
     */
    public ProductDto createProduct(ProductDto productDto) {
        Long id = idCounter.incrementAndGet();
        productDto.setId(id);
        productDatabase.put(id, productDto);
        return productDto;
    }

    /**
     * Get product by ID
     * @param id product ID
     * @return product data or null if not found
     */
    public ProductDto getProductById(Long id) {
        return productDatabase.getOrDefault(id, null);
    }

    /**
     * Update product
     * @param id product ID to update
     * @param updateDto new product data
     * @return updated product
     */
    public ProductDto updateProduct(Long id, ProductDto updateDto) {
        if (productDatabase.containsKey(id)) {
            ProductDto existingProduct = productDatabase.get(id);
            
            if (updateDto.getName() != null) {
                existingProduct.setName(updateDto.getName());
            }
            if (updateDto.getPrice() > 0) {
                existingProduct.setPrice(updateDto.getPrice());
            }
            if (updateDto.getQuantity() >= 0) {
                existingProduct.setQuantity(updateDto.getQuantity());
            }
            if (updateDto.getCategory() != null) {
                existingProduct.setCategory(updateDto.getCategory());
            }
            if (updateDto.getDescription() != null) {
                existingProduct.setDescription(updateDto.getDescription());
            }
            
            productDatabase.put(id, existingProduct);
            return existingProduct;
        }
        return null;
    }

    /**
     * Delete product
     * @param id product ID to delete
     * @return true if deleted, false if not found
     */
    public boolean deleteProduct(Long id) {
        if (productDatabase.containsKey(id)) {
            productDatabase.remove(id);
            return true;
        }
        return false;
    }

    /**
     * Get all products
     * @return map of all products
     */
    public Map<Long, ProductDto> getAllProducts() {
        return new HashMap<>(productDatabase);
    }
}
