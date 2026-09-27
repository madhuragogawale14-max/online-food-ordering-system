package com.example.foodordering.dto.request;

import jakarta.validation.constraints.NotNull;
import com.example.foodordering.entity.OrderStatus;

public class OrderStatusUpdateRequest {

    @NotNull(message = "Status is required")
    private OrderStatus status;

    public OrderStatus getStatus() { return status; }
    public void setStatus(OrderStatus status) { this.status = status; }
}
