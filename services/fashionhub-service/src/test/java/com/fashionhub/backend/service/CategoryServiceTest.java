package com.fashionhub.backend.service;

import com.fashionhub.backend.entity.Category;
import com.fashionhub.backend.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class CategoryServiceTest {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryRepository categoryRepository;

    private Category testCategory1;
    private Category testCategory2;

    @BeforeEach
    void setUp() {
        
        categoryRepository.deleteAll();

        
        testCategory1 = new Category("Electronics", "Electronic items and gadgets");
        testCategory2 = new Category("Clothing", "Fashion and apparel");

        categoryRepository.save(testCategory1);
        categoryRepository.save(testCategory2);
    }

    @Test
    void testGetAllCategories() {
        
        List<Category> categories = categoryService.getAllCategories();

        
        assertThat(categories).hasSize(2);
        assertThat(categories).extracting(Category::getCategoryName)
                .containsExactlyInAnyOrder("Electronics", "Clothing");
    }

    @Test
    void testGetCategoriesWithProductCount() {
        
        Map<String, Object> categoriesWithCount = categoryService.getCategoriesWithProductCount();

        
        assertThat(categoriesWithCount).hasSize(2);
        assertThat(categoriesWithCount).containsKeys("Electronics", "Clothing");

        Object electronicsData = categoriesWithCount.get("Electronics");
        assertThat(electronicsData).isNotNull();

        Object clothingData = categoriesWithCount.get("Clothing");
        assertThat(clothingData).isNotNull();
    }

    @Test
    void testGetAllCategoriesWhenEmpty() {
        
        categoryRepository.deleteAll();

        
        List<Category> categories = categoryService.getAllCategories();

        
        assertThat(categories).isEmpty();
    }

    @Test
    void testGetCategoriesWithProductCountWhenEmpty() {
        
        categoryRepository.deleteAll();

        
        Map<String, Object> categoriesWithCount = categoryService.getCategoriesWithProductCount();

        
        assertThat(categoriesWithCount).isEmpty();
    }
}
