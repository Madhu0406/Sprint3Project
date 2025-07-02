package com.fashionhub.backend.repository;

import com.fashionhub.backend.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    List<Product> findByCategoryCategoryId(Integer categoryId);

    @Query("SELECT p FROM Product p WHERE p.productName LIKE %:keyword% OR p.description LIKE %:keyword%")
    List<Product> findByProductNameContainingOrDescriptionContaining(@Param("keyword") String keyword);

    List<Product> findByBrand(String brand);

    @Query("SELECT p FROM Product p WHERE p.category.categoryId = :categoryId AND (p.productName LIKE %:keyword% OR p.description LIKE %:keyword%)")
    List<Product> findByCategoryAndKeyword(@Param("categoryId") Integer categoryId, @Param("keyword") String keyword);
}
