package com.example.foodordering.dto.response;

import java.math.BigDecimal;

public class FoodItemResponse {

    private Long id;
    private Long categoryId;
    private String categoryName;
    private String name;
    private String description;
    private BigDecimal price;
    private String imageUrl;
    private boolean available;

    public FoodItemResponse(Long id, Long categoryId, String categoryName, String name,
                             String description, BigDecimal price, String imageUrl, boolean available) {
        this.id = id;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
        this.available = available;
    }

    public Long getId() { return id; }
    public Long getCategoryId() { return categoryId; }
    public String getCategoryName() { return categoryName; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public BigDecimal getPrice() { return price; }
    public String getImageUrl() { return imageUrl; }
    public boolean isAvailable() { return available; }
}