package com.flogin.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flogin.dto.ProductDto;
import com.flogin.service.ProductService;

/**
 * REST Controller cho Product endpoints
 * Xử lý CRUD operations cho sản phẩm
 */
@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*")
public class ProductController {

    @Autowired
    private ProductService productService;

    /**
     * POST /api/products - Tạo sản phẩm mới
     * 
     * @param productDto Product data
     * @return Created product với status 201
     */
    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {
        if (productDto == null) {
            return ResponseEntity.badRequest().build();
        }

        ProductDto created = productService.createProduct(productDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * GET /api/products/{id} - Lấy sản phẩm theo ID
     * 
     * @param id Product ID
     * @return Product nếu tìm thấy, 404 nếu không
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {
        ProductDto product = productService.getProductById(id);
        
        if (product != null) {
            return ResponseEntity.ok(product);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * GET /api/products - Lấy tất cả sản phẩm
     * 
     * @return Map of all products
     */
    @GetMapping
    public ResponseEntity<Map<Long, ProductDto>> getAllProducts() {
        Map<Long, ProductDto> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    /**
     * PUT /api/products/{id} - Cập nhật sản phẩm
     * 
     * @param id Product ID
     * @param productDto Updated product data
     * @return Updated product nếu thành công, 404 nếu không tìm thấy
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(
            @PathVariable Long id,
            @RequestBody ProductDto productDto) {
        
        ProductDto updated = productService.updateProduct(id, productDto);
        
        if (updated != null) {
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * DELETE /api/products/{id} - Xóa sản phẩm
     * 
     * @param id Product ID
     * @return 204 No Content nếu thành công, 404 nếu không tìm thấy
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        boolean deleted = productService.deleteProduct(id);
        
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * GET /api/products/health - Health check endpoint
     * 
     * @return Success message
     */
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Product API is running");
    }
}
