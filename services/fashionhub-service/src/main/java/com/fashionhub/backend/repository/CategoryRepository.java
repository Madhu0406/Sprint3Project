package com.fashionhub.backend.repository;

import com.fashionhub.backend.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    @Query("SELECT c, COUNT(p) FROM Category c LEFT JOIN c.products p GROUP BY c")
    List<Object[]> findCategoriesWithProductCount();
}
