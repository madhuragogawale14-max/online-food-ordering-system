package com.example.foodordering.repository;

import com.example.foodordering.entity.FoodItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FoodItemRepository extends JpaRepository<FoodItem, Long> {
    List<FoodItem> findByCategoryId(Long categoryId);
    List<FoodItem> findByNameContainingIgnoreCase(String name);
}