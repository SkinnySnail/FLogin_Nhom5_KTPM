package com.flogin.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flogin.dto.ProductDto;
import com.flogin.service.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(com.flogin.controller.ProductController.class)
@DisplayName("Product API Integration Tests")
class ProductControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductService productService;

    @Test
    @DisplayName("GET /api/products - Lấy danh sách sản phẩm")
    void testGetAllProducts() throws Exception {
        List<ProductDto> products = Arrays.asList(
                new ProductDto("Laptop", 15000000.0, 10, "Electronics"),
                new ProductDto("Mouse", 200000.0, 50, "Electronics")
        );
        when(productService.getAllProducts()).thenReturn(products);

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name").value("Laptop"))
                .andExpect(jsonPath("$[1].name").value("Mouse"));
    }

    @Test
    @DisplayName("POST /api/products - Tạo sản phẩm mới")
    void testCreateProduct() throws Exception {
        ProductDto requestDto = new ProductDto("Laptop", 15000000.0, 10, "Electronics");
        ProductDto responseDto = new ProductDto("Laptop", 15000000.0, 10, "Electronics");
        responseDto.setId(1L);
        when(productService.createProduct(any(ProductDto.class))).thenReturn(responseDto);

        mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Laptop"));
    }

    @Test
    @DisplayName("GET /api/products/{id} - Lấy sản phẩm theo ID")
    void testGetProductById() throws Exception {
        ProductDto productDto = new ProductDto("Laptop", 15000000.0, 10, "Electronics");
        productDto.setId(1L);
        when(productService.getProductById(1L)).thenReturn(productDto);

        mockMvc.perform(get("/api/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Laptop"));
    }

    @Test
    @DisplayName("PUT /api/products/{id} - Cập nhật sản phẩm")
    void testUpdateProduct() throws Exception {
        ProductDto updateDto = new ProductDto("Laptop Updated", 14000000.0, 15, "Electronics");
        ProductDto responseDto = new ProductDto("Laptop Updated", 14000000.0, 15, "Electronics");
        responseDto.setId(1L);
        when(productService.updateProduct(eq(1L), any(ProductDto.class))).thenReturn(responseDto);

        mockMvc.perform(put("/api/products/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Laptop Updated"));
    }

    @Test
    @DisplayName("DELETE /api/products/{id} - Xóa sản phẩm")
    void testDeleteProduct() throws Exception {
        when(productService.deleteProduct(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/products/1"))
                .andExpect(status().isNoContent());
        // Xóa thành công trả về 204 No Content
    }
}
