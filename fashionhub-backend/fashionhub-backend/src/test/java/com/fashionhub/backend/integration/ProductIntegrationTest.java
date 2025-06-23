package com.fashionhub.backend.integration;

import com.fashionhub.backend.entity.Category;
import com.fashionhub.backend.entity.Product;
import com.fashionhub.backend.repository.CategoryRepository;
import com.fashionhub.backend.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc  // This annotation is REQUIRED for MockMvc
@ActiveProfiles("test")
@Transactional
class ProductIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        productRepository.deleteAll();
        categoryRepository.deleteAll();

        // Create test data
        Category category = new Category("Integration Test Category", "Test Description");
        category = categoryRepository.save(category);

        Product product = new Product();
        product.setProductId("INT001");
        product.setProductName("Integration Test Product");
        product.setCategory(category);
        product.setDescription("Integration test description");
        product.setPrice(new BigDecimal("199.99"));
        product.setOriginalPrice(new BigDecimal("299.99"));
        product.setDiscountPercent(33);
        product.setQuantityInStock(5);
        product.setBrand("IntegrationBrand");
        product.setImageUrl("integration-test.jpg");

        productRepository.save(product);
    }

    @Test
    void testGetAllProductsIntegration() throws Exception {
        mockMvc.perform(get("/products")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].productId").value("INT001"))
                .andExpect(jsonPath("$[0].productName").value("Integration Test Product"));
    }

    @Test
    void testGetProductByIdIntegration() throws Exception {
        mockMvc.perform(get("/products/INT001")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value("INT001"))
                .andExpect(jsonPath("$.price").value(199.99));
    }

    @Test
    void testSearchProductsIntegration() throws Exception {
        mockMvc.perform(get("/products/search")
                        .param("keyword", "Integration")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].productName").value("Integration Test Product"));
    }

    @Test
    void testGetProductsByCategoryIntegration() throws Exception {
        // Get the category ID from the saved category
        Category savedCategory = categoryRepository.findAll().get(0);

        mockMvc.perform(get("/products/category/" + savedCategory.getCategoryId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].productId").value("INT001"));
    }
}
