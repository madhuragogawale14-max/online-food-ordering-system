package com.example.foodordering.controller;

import com.example.foodordering.dto.request.FoodItemRequest;
import com.example.foodordering.dto.response.FoodItemResponse;
import com.example.foodordering.service.FoodItemService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/food")
public class FoodItemController {

    private final FoodItemService foodItemService;

    public FoodItemController(FoodItemService foodItemService) {
        this.foodItemService = foodItemService;
    }

    @GetMapping
    public ResponseEntity<List<FoodItemResponse>> getAll() {
        return ResponseEntity.ok(foodItemService.getAll());
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<FoodItemResponse>> getByCategory(@PathVariable Long categoryId) {
        return ResponseEntity.ok(foodItemService.getByCategory(categoryId));
    }

    @GetMapping("/search")
    public ResponseEntity<List<FoodItemResponse>> search(@RequestParam String name) {
        return ResponseEntity.ok(foodItemService.search(name));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FoodItemResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(foodItemService.getById(id));
    }

    @PostMapping
    public ResponseEntity<FoodItemResponse> create(@Valid @RequestBody FoodItemRequest request) {
        return ResponseEntity.ok(foodItemService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FoodItemResponse> update(@PathVariable Long id, @Valid @RequestBody FoodItemRequest request) {
        return ResponseEntity.ok(foodItemService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        foodItemService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
