package com.flogin.e2e;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.flogin.dto.ProductDto;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductE2ETest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testCreateProduct() {
        String url = "http://localhost:" + port + "/api/products";
        ProductDto request = new ProductDto();
        request.setName("Laptop Dell");
        request.setPrice(15000000);
        request.setQuantity(10);
        request.setCategory("Electronics");

        ResponseEntity<ProductDto> response = restTemplate.postForEntity(url, request, ProductDto.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Laptop Dell", response.getBody().getName());
    }

    @Test
    void testGetProductById_NotFound() {
        String url = "http://localhost:" + port + "/api/products/99999";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testGetAllProducts() {
        String url = "http://localhost:" + port + "/api/products";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }
}
