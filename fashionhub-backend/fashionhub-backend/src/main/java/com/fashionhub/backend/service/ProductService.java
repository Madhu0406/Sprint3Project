package com.fashionhub.backend.service;

import com.fashionhub.backend.dto.ProductResponse;
import com.fashionhub.backend.entity.Product;
import com.fashionhub.backend.exception.ProductNotFoundException;
import com.fashionhub.backend.repository.ProductRepository;
import com.fashionhub.backend.repository.ProductSizeRepository;
import com.fashionhub.backend.repository.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductSizeRepository productSizeRepository;

    @Autowired
    private OfferRepository offerRepository;

    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::convertToProductResponse)
                .collect(Collectors.toList());
    }

    public ProductResponse getProductById(String productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + productId));
        return convertToProductResponse(product);
    }

    public List<ProductResponse> getProductsByCategory(Integer categoryId) {
        return productRepository.findByCategoryCategoryId(categoryId).stream()
                .map(this::convertToProductResponse)
                .collect(Collectors.toList());
    }

    public List<ProductResponse> searchProducts(String keyword) {
        return productRepository.findByProductNameContainingOrDescriptionContaining(keyword).stream()
                .map(this::convertToProductResponse)
                .collect(Collectors.toList());
    }

    public List<ProductResponse> searchProductsByCategoryAndKeyword(Integer categoryId, String keyword) {
        return productRepository.findByCategoryAndKeyword(categoryId, keyword).stream()
                .map(this::convertToProductResponse)
                .collect(Collectors.toList());
    }

    private ProductResponse convertToProductResponse(Product product) {
        ProductResponse response = new ProductResponse();
        response.setProductId(product.getProductId());
        response.setProductName(product.getProductName());
        response.setCategoryName(product.getCategory().getCategoryName());
        response.setCategoryId(product.getCategory().getCategoryId());
        response.setDescription(product.getDescription());
        response.setPrice(product.getPrice());
        response.setOriginalPrice(product.getOriginalPrice());
        response.setDiscountPercent(product.getDiscountPercent());
        response.setQuantityInStock(product.getQuantityInStock());
        response.setBrand(product.getBrand());
        response.setImageUrl(product.getImageUrl());
        response.setCreatedAt(product.getCreatedAt());

        
        List<String> sizes = productSizeRepository.findByProductProductId(product.getProductId())
                .stream()
                .map(size -> size.getSize())
                .collect(Collectors.toList());
        response.setSizes(sizes);

    
        List<String> offers = offerRepository.findByProductProductId(product.getProductId())
                .stream()
                .map(offer -> offer.getOfferDescription())
                .collect(Collectors.toList());
        response.setOffers(offers);

        return response;
    }
}
