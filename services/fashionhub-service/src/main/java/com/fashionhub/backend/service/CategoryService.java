package com.fashionhub.backend.service;

import com.fashionhub.backend.entity.Category;
import com.fashionhub.backend.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Map<String, Object> getCategoriesWithProductCount() {
        List<Object[]> results = categoryRepository.findCategoriesWithProductCount();

        return results.stream()
                .collect(Collectors.toMap(
                        result -> ((Category) result[0]).getCategoryName(),
                        result -> Map.of(
                                "category", result[0],
                                "productCount", result[1] != null ? result[1] : 0L
                        )
                ));
    }
}
