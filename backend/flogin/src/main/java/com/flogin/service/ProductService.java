package com.flogin.service;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;

import com.flogin.dto.ProductDto;

import com.flogin.entity.Product;
import com.flogin.repository.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    /**
     * Create a new product
     * @param productDto product data to create
     * @return created product with ID
     */
    public ProductDto createProduct(ProductDto productDto) {
        Product product = dtoToEntity(productDto);
        Product saved = productRepository.save(product);
        return entityToDto(saved);
    }

    /**
     * Get product by ID
     * @param id product ID
     * @return product data or null if not found
     */
    public ProductDto getProductById(Long id) {
        Optional<Product> productOpt = productRepository.findById(id);
        return productOpt.map(this::entityToDto).orElse(null);
    }

    /**
     * Update product
     * @param id product ID to update
     * @param updateDto new product data
     * @return updated product
     */
    public ProductDto updateProduct(Long id, ProductDto updateDto) {
        Optional<Product> productOpt = productRepository.findById(id);
        if (productOpt.isPresent()) {
            Product product = productOpt.get();
            if (updateDto.getName() != null) {
                product.setName(updateDto.getName());
            }
            if (updateDto.getPrice() > 0) {
                product.setPrice(updateDto.getPrice());
            }
            if (updateDto.getQuantity() >= 0) {
                product.setQuantity(updateDto.getQuantity());
            }
            if (updateDto.getCategory() != null) {
                product.setCategory(updateDto.getCategory());
            }
            if (updateDto.getDescription() != null) {
                product.setDescription(updateDto.getDescription());
            }
            Product saved = productRepository.save(product);
            return entityToDto(saved);
        }
        return null;
    }

    /**
     * Delete product
     * @param id product ID to delete
     * @return true if deleted, false if not found
     */
    public boolean deleteProduct(Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Get all products
     * @return list of all products
     */
    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }

    // Helper: Convert DTO to Entity
    private Product dtoToEntity(ProductDto dto) {
        Product entity = new Product();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setPrice(dto.getPrice());
        entity.setQuantity(dto.getQuantity());
        entity.setCategory(dto.getCategory());
        entity.setDescription(dto.getDescription());
        return entity;
    }

    // Helper: Convert Entity to DTO
    private ProductDto entityToDto(Product entity) {
        ProductDto dto = new ProductDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setPrice(entity.getPrice());
        dto.setQuantity(entity.getQuantity());
        dto.setCategory(entity.getCategory());
        dto.setDescription(entity.getDescription());
        return dto;
    }
}
