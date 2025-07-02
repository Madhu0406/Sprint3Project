package com.fashionhub.backend.repository;

import com.fashionhub.backend.entity.Category;
import com.fashionhub.backend.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private Category testCategory;
    private Product testProduct;

    @BeforeEach
    void setUp() {
        productRepository.deleteAll();
        categoryRepository.deleteAll();

        
        testCategory = new Category("Test Category", "Test Description");
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
    void testFindById() {
        
        Optional<Product> found = productRepository.findById("TEST001");

        
        assertThat(found).isPresent();
        assertThat(found.get().getProductName()).isEqualTo("Test Product");
        assertThat(found.get().getPrice()).isEqualTo(new BigDecimal("99.99"));
    }

    @Test
    void testFindByCategoryCategoryId() {
        
        List<Product> products = productRepository.findByCategoryCategoryId(testCategory.getCategoryId());

        
        assertThat(products).hasSize(1);
        assertThat(products.get(0).getProductName()).isEqualTo("Test Product");
    }

    @Test
    void testFindByProductNameContaining() {
        
        List<Product> products = productRepository.findByProductNameContainingOrDescriptionContaining("Test");

        
        assertThat(products).hasSize(1);
        assertThat(products.get(0).getProductId()).isEqualTo("TEST001");
    }

    @Test
    void testFindByBrand() {
        
        List<Product> products = productRepository.findByBrand("TestBrand");

        
        assertThat(products).hasSize(1);
        assertThat(products.get(0).getBrand()).isEqualTo("TestBrand");
    }

    @Test
    void testFindByCategoryAndKeyword() {
        
        List<Product> products = productRepository.findByCategoryAndKeyword(testCategory.getCategoryId(), "Test");

        
        assertThat(products).hasSize(1);
        assertThat(products.get(0).getProductName()).isEqualTo("Test Product");
    }
}
