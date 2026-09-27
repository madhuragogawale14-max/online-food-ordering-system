package com.example.foodordering.dto.response;

import java.math.BigDecimal;

public class OrderItemResponse {

    private Long id;
    private String foodName;
    private BigDecimal unitPrice;
    private Integer quantity;

    public OrderItemResponse(Long id, String foodName, BigDecimal unitPrice, Integer quantity) {
        this.id = id;
        this.foodName = foodName;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }

    public Long getId() { return id; }
    public String getFoodName() { return foodName; }
    public BigDecimal getUnitPrice() { return unitPrice; }
    public Integer getQuantity() { return quantity; }
}
