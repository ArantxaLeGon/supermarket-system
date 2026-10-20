package com.supermarket.Category.repository;

import java.util.Optional;
import com.supermarket.Category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.stereotype.Repository;

// @Repository 
public interface CategoryRepository extends JpaRepository<Category,Long> {
    Optional<Category> findFirstByName(String name);
}
