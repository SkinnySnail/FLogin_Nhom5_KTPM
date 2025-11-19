package com.flogin.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flogin.dto.ProductDto;
import com.flogin.service.ProductService;

import java.util.ArrayList;
import java.util.List;

/**
 * Integration Tests cho ProductController
 * Test CRUD API endpoints với MockMvc
 */
@WebMvcTest(ProductController.class)
@DisplayName("Product Controller Integration Tests")
class ProductControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductService productService;

    @Test
    @DisplayName("TC_PRODUCT_INT_01: POST /api/products - Tạo sản phẩm thành công (201)")
    void testCreateProduct_Success() throws Exception {
        // Arrange
        ProductDto requestDto = new ProductDto();
        requestDto.setName("Laptop Dell");
        requestDto.setPrice(15000000);
        requestDto.setQuantity(10);
        requestDto.setCategory("Electronics");

        ProductDto responseDto = new ProductDto();
        responseDto.setId(1L);
        responseDto.setName("Laptop Dell");
        responseDto.setPrice(15000000);
        responseDto.setQuantity(10);
        responseDto.setCategory("Electronics");

        when(productService.createProduct(any(ProductDto.class)))
            .thenReturn(responseDto);

        // Act & Assert
        mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.name").value("Laptop Dell"))
            .andExpect(jsonPath("$.price").value(15000000))
            .andExpect(jsonPath("$.quantity").value(10))
            .andExpect(jsonPath("$.category").value("Electronics"));
    }

    @Test
    @DisplayName("TC_PRODUCT_INT_02: GET /api/products/{id} - Lấy sản phẩm thành công (200)")
    void testGetProductById_Success() throws Exception {
        // Arrange
        ProductDto mockProduct = new ProductDto();
        mockProduct.setId(1L);
        mockProduct.setName("Laptop Dell");
        mockProduct.setPrice(15000000);
        mockProduct.setQuantity(10);
        mockProduct.setCategory("Electronics");

        when(productService.getProductById(1L))
            .thenReturn(mockProduct);

        // Act & Assert
        mockMvc.perform(get("/api/products/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.name").value("Laptop Dell"))
            .andExpect(jsonPath("$.price").value(15000000));
    }

    @Test
    @DisplayName("TC_PRODUCT_INT_03: GET /api/products/{id} - Không tìm thấy sản phẩm (404)")
    void testGetProductById_NotFound() throws Exception {
        // Arrange
        when(productService.getProductById(999L))
            .thenReturn(null);

        // Act & Assert
        mockMvc.perform(get("/api/products/999"))
            .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("TC_PRODUCT_INT_04: GET /api/products - Lấy tất cả sản phẩm (200)")
    void testGetAllProducts_Success() throws Exception {
        // Arrange
        List<ProductDto> mockProducts = new ArrayList<>();
        ProductDto product1 = new ProductDto();
        product1.setId(1L);
        product1.setName("Laptop Dell");
        product1.setPrice(15000000);
        product1.setQuantity(10);
        ProductDto product2 = new ProductDto();
        product2.setId(2L);
        product2.setName("Mouse Logitech");
        product2.setPrice(200000);
        product2.setQuantity(50);
        mockProducts.add(product1);
        mockProducts.add(product2);
        when(productService.getAllProducts())
            .thenReturn(mockProducts);
        // Act & Assert
        mockMvc.perform(get("/api/products"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$[0].name").value("Laptop Dell"))
            .andExpect(jsonPath("$[1].name").value("Mouse Logitech"));
    }

    @Test
    @DisplayName("TC_PRODUCT_INT_05: PUT /api/products/{id} - Cập nhật sản phẩm thành công (200)")
    void testUpdateProduct_Success() throws Exception {
        // Arrange
        ProductDto updateDto = new ProductDto();
        updateDto.setName("Laptop Dell Updated");
        updateDto.setPrice(14000000);
        updateDto.setQuantity(15);

        ProductDto responseDto = new ProductDto();
        responseDto.setId(1L);
        responseDto.setName("Laptop Dell Updated");
        responseDto.setPrice(14000000);
        responseDto.setQuantity(15);
        responseDto.setCategory("Electronics");

        when(productService.updateProduct(eq(1L), any(ProductDto.class)))
            .thenReturn(responseDto);

        // Act & Assert
        mockMvc.perform(put("/api/products/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateDto)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.name").value("Laptop Dell Updated"))
            .andExpect(jsonPath("$.price").value(14000000))
            .andExpect(jsonPath("$.quantity").value(15));
    }

    @Test
    @DisplayName("TC_PRODUCT_INT_06: PUT /api/products/{id} - Cập nhật sản phẩm không tồn tại (404)")
    void testUpdateProduct_NotFound() throws Exception {
        // Arrange
        ProductDto updateDto = new ProductDto();
        updateDto.setName("Non-existent Product");

        when(productService.updateProduct(eq(999L), any(ProductDto.class)))
            .thenReturn(null);

        // Act & Assert
        mockMvc.perform(put("/api/products/999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateDto)))
            .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("TC_PRODUCT_INT_07: DELETE /api/products/{id} - Xóa sản phẩm thành công (204)")
    void testDeleteProduct_Success() throws Exception {
        // Arrange
        when(productService.deleteProduct(1L))
            .thenReturn(true);

        // Act & Assert
        mockMvc.perform(delete("/api/products/1"))
            .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("TC_PRODUCT_INT_08: DELETE /api/products/{id} - Xóa sản phẩm không tồn tại (404)")
    void testDeleteProduct_NotFound() throws Exception {
        // Arrange
        when(productService.deleteProduct(999L))
            .thenReturn(false);

        // Act & Assert
        mockMvc.perform(delete("/api/products/999"))
            .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("TC_PRODUCT_INT_09: POST /api/products - Request body rỗng (400)")
    void testCreateProduct_EmptyBody() throws Exception {
        // Act & Assert
        mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(""))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("TC_PRODUCT_INT_10: GET /api/products/health - Health check")
    void testHealthCheck() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/api/products/health"))
            .andExpect(status().isOk())
            .andExpect(content().string("Product API is running"));
    }

    @Test
    @DisplayName("TC_PRODUCT_INT_11: POST /api/products - Content-Type validation")
    void testCreateProduct_InvalidContentType() throws Exception {
        // Arrange
        ProductDto productDto = new ProductDto();
        productDto.setName("Test Product");

        // Act & Assert
        mockMvc.perform(post("/api/products")
                .contentType(MediaType.TEXT_PLAIN)
                .content(objectMapper.writeValueAsString(productDto)))
            .andExpect(status().isUnsupportedMediaType());
    }

    @Test
    @DisplayName("TC_PRODUCT_INT_12: GET /api/products/{id} - Response structure validation")
    void testGetProductById_ResponseStructure() throws Exception {
        // Arrange
        ProductDto mockProduct = new ProductDto();
        mockProduct.setId(1L);
        mockProduct.setName("Laptop Dell");
        mockProduct.setPrice(15000000);
        mockProduct.setQuantity(10);
        mockProduct.setCategory("Electronics");
        mockProduct.setDescription("High performance laptop");

        when(productService.getProductById(1L))
            .thenReturn(mockProduct);

        // Act & Assert
        mockMvc.perform(get("/api/products/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").exists())
            .andExpect(jsonPath("$.name").exists())
            .andExpect(jsonPath("$.price").exists())
            .andExpect(jsonPath("$.quantity").exists())
            .andExpect(jsonPath("$.category").exists())
            .andExpect(jsonPath("$.id").isNumber())
            .andExpect(jsonPath("$.name").isString())
            .andExpect(jsonPath("$.price").isNumber())
            .andExpect(jsonPath("$.quantity").isNumber());
    }

    @Test
    @DisplayName("TC_PRODUCT_INT_13: PUT /api/products/{id} - Partial update")
    void testUpdateProduct_PartialUpdate() throws Exception {
        // Arrange - Chỉ cập nhật price
        ProductDto updateDto = new ProductDto();
        updateDto.setPrice(13000000);

        ProductDto responseDto = new ProductDto();
        responseDto.setId(1L);
        responseDto.setName("Laptop Dell"); // Giữ nguyên
        responseDto.setPrice(13000000); // Đã update
        responseDto.setQuantity(10); // Giữ nguyên
        responseDto.setCategory("Electronics");

        when(productService.updateProduct(eq(1L), any(ProductDto.class)))
            .thenReturn(responseDto);

        // Act & Assert
        mockMvc.perform(put("/api/products/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateDto)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.price").value(13000000));
    }

    @Test
    @DisplayName("TC_PRODUCT_INT_14: CORS headers validation")
    void testProduct_CORSHeaders() throws Exception {
        // Arrange
        ProductDto mockProduct = new ProductDto();
        mockProduct.setId(1L);
        mockProduct.setName("Test Product");

        when(productService.getProductById(1L))
            .thenReturn(mockProduct);

        // Act & Assert
        mockMvc.perform(get("/api/products/1")
                .header("Origin", "http://localhost:3000"))
            .andExpect(status().isOk())
            .andExpect(header().exists("Access-Control-Allow-Origin"));
    }

    @Test
    @DisplayName("TC_PRODUCT_INT_15: GET /api/products - Empty list")
    void testGetAllProducts_EmptyList() throws Exception {
        // Arrange
        List<ProductDto> emptyList = new ArrayList<>();
        when(productService.getAllProducts())
            .thenReturn(emptyList);
        // Act & Assert
        mockMvc.perform(get("/api/products"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(0)));
    }
}
