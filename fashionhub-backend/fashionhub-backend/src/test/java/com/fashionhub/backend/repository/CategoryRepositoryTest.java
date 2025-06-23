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

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    private Category testCategory;

    @BeforeEach
    void setUp() {
        productRepository.deleteAll();
        categoryRepository.deleteAll();

        testCategory = new Category("Electronics", "Electronic items and gadgets");
        testCategory = categoryRepository.save(testCategory);

        
        Product product = new Product();
        product.setProductId("ELEC001");
        product.setProductName("Test Electronics");
        product.setCategory(testCategory);
        product.setDescription("Test electronic product");
        product.setPrice(new BigDecimal("199.99"));
        product.setOriginalPrice(new BigDecimal("299.99"));
        product.setDiscountPercent(33);
        product.setQuantityInStock(5);
        product.setBrand("TechBrand");
        product.setImageUrl("electronics.jpg");

        productRepository.save(product);
    }

    @Test
    void testFindAll() {
        
        List<Category> categories = categoryRepository.findAll();

        assertThat(categories).hasSize(1);
        assertThat(categories.get(0).getCategoryName()).isEqualTo("Electronics");
    }

    @Test
    void testFindCategoriesWithProductCount() {
        
        List<Object[]> results = categoryRepository.findCategoriesWithProductCount();

        
        assertThat(results).hasSize(1);
        Object[] result = results.get(0);
        Category category = (Category) result[0];
        Long count = (Long) result[1];

        assertThat(category.getCategoryName()).isEqualTo("Electronics");
        assertThat(count).isEqualTo(1L);
    }
}
