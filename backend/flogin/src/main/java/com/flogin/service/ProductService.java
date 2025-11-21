package com.flogin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.flogin.dto.ProductDto;
import com.flogin.entity.Product;
import com.flogin.repository.ProductRepository;

@Service
public class ProductService {
        // Helper: Convert Product entity to ProductDto
        public ProductDto toDto(Product product) {
            if (product == null) return null;
            ProductDto dto = new ProductDto();
            dto.setId(product.getId());
            dto.setName(product.getProductName());
            dto.setPrice(product.getPrice());
            dto.setQuantity(product.getQuantity());
            dto.setCategory(product.getCategory());
            dto.setDescription(product.getDescription());
            return dto;
        }

        // Helper: Convert ProductDto to Product entity
        public Product fromDto(ProductDto dto) {
            if (dto == null) return null;
            Product product = new Product();
            product.setId(dto.getId());
            product.setProductName(dto.getName());
            product.setPrice(dto.getPrice());
            product.setQuantity(dto.getQuantity());
            product.setCategory(dto.getCategory());
            product.setDescription(dto.getDescription());
            return product;
        }
    @Autowired
    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductService() {
        // Default constructor
    }

    /**
     * Create a new product
     * 
     * @param productDto product data to create
     * @return created product with ID
     */
    public ProductDto createProduct(ProductDto productDto) {
        Product product = productDto.toEntity();
        Product saved = productRepository.save(product);
        return toDto(saved);
    }

    /**
     * Get product by ID
     * 
     * @param id product ID
     * @return product data or null if not found
     */
    public ProductDto getProductById(Long id) {
        Product product = productRepository.findById(id).orElse(null);
        return toDto(product);
    }

    /**
     * Update product
     * 
     * @param id         product ID to update
     * @param productDto new product data
     * @return updated product
     */
    public ProductDto updateProduct(Long id, ProductDto productDto) {
        return productRepository.findById(id).map(existingProduct -> {
            if (productDto.getName() != null && !productDto.getName().trim().isEmpty()) {
                existingProduct.setProductName(productDto.getName());
            }
            if (productDto.getPrice() != null && productDto.getPrice() > 0) {
                existingProduct.setPrice(productDto.getPrice());
            }
            if (productDto.getQuantity() != null && productDto.getQuantity() >= 0) {
                existingProduct.setQuantity(productDto.getQuantity());
            }
            if (productDto.getCategory() != null && !productDto.getCategory().trim().isEmpty()) {
                existingProduct.setCategory(productDto.getCategory());
            }
            if (productDto.getDescription() != null) {
                existingProduct.setDescription(productDto.getDescription());
            }
            Product saved = productRepository.save(existingProduct);
            return toDto(saved);
        }).orElse(null);
    }

    /**
     * Delete product
     * 
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
     * 
     * @return list of all products
     */
    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(this::toDto).toList();
    }
}
