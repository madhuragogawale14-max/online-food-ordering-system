package com.example.foodordering.dto.response;

import com.example.foodordering.entity.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class OrderResponse {

    private Long id;
    private Long userId;
    private String userName;
    private String deliveryAddress;
    private OrderStatus status;
    private BigDecimal totalAmount;
    private String paymentMethod;
    private LocalDateTime createdAt;
    private List<OrderItemResponse> items;

    public OrderResponse(Long id, Long userId, String userName, String deliveryAddress, OrderStatus status,
                          BigDecimal totalAmount, String paymentMethod, LocalDateTime createdAt,
                          List<OrderItemResponse> items) {
        this.id = id;
        this.userId = userId;
        this.userName = userName;
        this.deliveryAddress = deliveryAddress;
        this.status = status;
        this.totalAmount = totalAmount;
        this.paymentMethod = paymentMethod;
        this.createdAt = createdAt;
        this.items = items;
    }

    public Long getId() { return id; }
    public Long getUserId() { return userId; }
    public String getUserName() { return userName; }
    public String getDeliveryAddress() { return deliveryAddress; }
    public OrderStatus getStatus() { return status; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public String getPaymentMethod() { return paymentMethod; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public List<OrderItemResponse> getItems() { return items; }
}
