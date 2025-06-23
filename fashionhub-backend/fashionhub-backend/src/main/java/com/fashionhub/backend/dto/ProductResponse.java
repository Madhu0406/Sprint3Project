package com.fashionhub.backend.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class ProductResponse {
    private String productId;
    private String productName;
    private String categoryName;
    private Integer categoryId;
    private String description;
    private BigDecimal price;
    private BigDecimal originalPrice;
    private Integer discountPercent;
    private Integer quantityInStock;
    private String brand;
    private String imageUrl;
    private LocalDateTime createdAt;
    private List<String> sizes;
    private List<String> offers;

    
    public ProductResponse() {}

    
    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }

    public Integer getCategoryId() { return categoryId; }
    public void setCategoryId(Integer categoryId) { this.categoryId = categoryId; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public BigDecimal getOriginalPrice() { return originalPrice; }
    public void setOriginalPrice(BigDecimal originalPrice) { this.originalPrice = originalPrice; }

    public Integer getDiscountPercent() { return discountPercent; }
    public void setDiscountPercent(Integer discountPercent) { this.discountPercent = discountPercent; }

    public Integer getQuantityInStock() { return quantityInStock; }
    public void setQuantityInStock(Integer quantityInStock) { this.quantityInStock = quantityInStock; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public List<String> getSizes() { return sizes; }
    public void setSizes(List<String> sizes) { this.sizes = sizes; }

    public List<String> getOffers() { return offers; }
    public void setOffers(List<String> offers) { this.offers = offers; }
}
