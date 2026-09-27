package com.example.foodordering.service;

import com.example.foodordering.dto.request.FoodItemRequest;
import com.example.foodordering.dto.response.FoodItemResponse;
import com.example.foodordering.entity.Category;
import com.example.foodordering.entity.FoodItem;
import com.example.foodordering.exception.ResourceNotFoundException;
import com.example.foodordering.repository.CategoryRepository;
import com.example.foodordering.repository.FoodItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodItemService {

    private final FoodItemRepository foodItemRepository;
    private final CategoryRepository categoryRepository;

    public FoodItemService(FoodItemRepository foodItemRepository, CategoryRepository categoryRepository) {
        this.foodItemRepository = foodItemRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<FoodItemResponse> getAll() {
        return foodItemRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    public List<FoodItemResponse> getByCategory(Long categoryId) {
        return foodItemRepository.findByCategoryId(categoryId).stream()
                .map(this::toResponse)
                .toList();
    }

    public List<FoodItemResponse> search(String name) {
        return foodItemRepository.findByNameContainingIgnoreCase(name).stream()
                .map(this::toResponse)
                .toList();
    }

    public FoodItemResponse getById(Long id) {
        FoodItem item = foodItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Food item not found with id: " + id));
        return toResponse(item);
    }

    public FoodItemResponse create(FoodItemRequest request) {
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + request.getCategoryId()));

        FoodItem item = new FoodItem();
        item.setCategory(category);
        item.setName(request.getName());
        item.setDescription(request.getDescription());
        item.setPrice(request.getPrice());
        item.setImageUrl(request.getImageUrl());
        item.setAvailable(request.isAvailable());

        foodItemRepository.save(item);
        return toResponse(item);
    }

    public FoodItemResponse update(Long id, FoodItemRequest request) {
        FoodItem item = foodItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Food item not found with id: " + id));

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + request.getCategoryId()));

        item.setCategory(category);
        item.setName(request.getName());
        item.setDescription(request.getDescription());
        item.setPrice(request.getPrice());
        item.setImageUrl(request.getImageUrl());
        item.setAvailable(request.isAvailable());

        foodItemRepository.save(item);
        return toResponse(item);
    }

    public void delete(Long id) {
        FoodItem item = foodItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Food item not found with id: " + id));
        foodItemRepository.delete(item);
    }

    private FoodItemResponse toResponse(FoodItem item) {
        return new FoodItemResponse(
                item.getId(),
                item.getCategory().getId(),
                item.getCategory().getName(),
                item.getName(),
                item.getDescription(),
                item.getPrice(),
                item.getImageUrl(),
                item.isAvailable()
        );
    }
}
