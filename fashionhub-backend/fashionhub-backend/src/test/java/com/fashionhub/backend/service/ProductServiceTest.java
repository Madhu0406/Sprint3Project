package com.fashionhub.backend.service;

import com.fashionhub.backend.dto.ProductResponse;
import com.fashionhub.backend.entity.Category;
import com.fashionhub.backend.entity.Product;
import com.fashionhub.backend.exception.ProductNotFoundException;
import com.fashionhub.backend.repository.CategoryRepository;
import com.fashionhub.backend.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private Product testProduct;
    private Category testCategory;

    @BeforeEach
    void setUp() {
        productRepository.deleteAll();
        categoryRepository.deleteAll();

        testCategory = new Category("Electronics", "Electronic items");
        testCategory = categoryRepository.save(testCategory);

        testProduct = new Product();
        testProduct.setProductId("TEST001");
        testProduct.setProductName("Test Product");
        testProduct.setCategory(testCategory);
        testProduct.setDescription("Test Description");
        testProduct.setPrice(new BigDecimal("99.99"));
        testProduct.setOriginalPrice(new BigDecimal("149.99"));
        testProduct.setDiscountPercent(33);
        testProduct.setQuantityInStock(10);
        testProduct.setBrand("TestBrand");
        testProduct.setImageUrl("test-image.jpg");

        productRepository.save(testProduct);
    }

    @Test
    void testGetAllProducts() {
        // When
        List<ProductResponse> products = productService.getAllProducts();

        // Then
        assertThat(products).hasSize(1);
        assertThat(products.get(0).getProductId()).isEqualTo("TEST001");
        assertThat(products.get(0).getProductName()).isEqualTo("Test Product");
    }

    @Test
    void testGetProductById() {
        // When
        ProductResponse product = productService.getProductById("TEST001");

        // Then
        assertThat(product).isNotNull();
        assertThat(product.getProductId()).isEqualTo("TEST001");
        assertThat(product.getProductName()).isEqualTo("Test Product");
    }

    @Test
    void testGetProductByIdNotFound() {
        // When & Then
        assertThatThrownBy(() -> productService.getProductById("NONEXISTENT"))
                .isInstanceOf(ProductNotFoundException.class)
                .hasMessageContaining("Product not found with ID: NONEXISTENT");
    }

    @Test
    void testGetProductsByCategory() {
        // When
        List<ProductResponse> products = productService.getProductsByCategory(testCategory.getCategoryId());

        // Then
        assertThat(products).hasSize(1);
        assertThat(products.get(0).getCategoryId()).isEqualTo(testCategory.getCategoryId());
    }

    @Test
    void testSearchProducts() {
        // When
        List<ProductResponse> products = productService.searchProducts("Test");

        // Then
        assertThat(products).hasSize(1);
        assertThat(products.get(0).getProductName()).contains("Test");
    }

    @Test
    void testSearchProductsNoResults() {
        // When
        List<ProductResponse> products = productService.searchProducts("NonExistent");

        // Then
        assertThat(products).isEmpty();
    }
}
