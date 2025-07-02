package com.fashionhub.backend.controller;

import com.fashionhub.backend.entity.Category;
import com.fashionhub.backend.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/categories")
@CrossOrigin(origins = "http://localhost:3000")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/with-count")
    public ResponseEntity<Map<String, Object>> getCategoriesWithProductCount() {
        Map<String, Object> categoriesWithCount = categoryService.getCategoriesWithProductCount();
        return ResponseEntity.ok(categoriesWithCount);
    }
}
