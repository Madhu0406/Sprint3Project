package com.fashionhub.backend.controller;

import com.fashionhub.backend.dto.ProductResponse;
import com.fashionhub.backend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@CrossOrigin(origins = "http://localhost:3000")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        List<ProductResponse> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable String productId) {
        ProductResponse product = productService.getProductById(productId);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ProductResponse>> getProductsByCategory(@PathVariable Integer categoryId) {
        List<ProductResponse> products = productService.getProductsByCategory(categoryId);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductResponse>> searchProducts(@RequestParam String keyword) {
        List<ProductResponse> products = productService.searchProducts(keyword);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/search/category")
    public ResponseEntity<List<ProductResponse>> searchProductsByCategoryAndKeyword(
            @RequestParam Integer categoryId,
            @RequestParam String keyword) {
        List<ProductResponse> products = productService.searchProductsByCategoryAndKeyword(categoryId, keyword);
        return ResponseEntity.ok(products);
    }
}
