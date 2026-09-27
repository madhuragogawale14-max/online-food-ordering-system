package com.example.foodordering.service;

import com.example.foodordering.dto.request.OrderItemRequest;
import com.example.foodordering.dto.request.PlaceOrderRequest;
import com.example.foodordering.dto.response.OrderItemResponse;
import com.example.foodordering.dto.response.OrderResponse;
import com.example.foodordering.entity.*;
import com.example.foodordering.exception.BadRequestException;
import com.example.foodordering.exception.ResourceNotFoundException;
import com.example.foodordering.repository.FoodItemRepository;
import com.example.foodordering.repository.OrderRepository;
import com.example.foodordering.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final FoodItemRepository foodItemRepository;

    public OrderService(OrderRepository orderRepository, UserRepository userRepository,
                         FoodItemRepository foodItemRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.foodItemRepository = foodItemRepository;
    }

    public OrderResponse placeOrder(PlaceOrderRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + request.getUserId()));

        Order order = new Order();
        order.setUser(user);
        order.setDeliveryAddress(request.getDeliveryAddress());
        order.setPaymentMethod(request.getPaymentMethod());
        order.setStatus(OrderStatus.PENDING);

        List<OrderItem> orderItems = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (OrderItemRequest itemRequest : request.getItems()) {
            FoodItem foodItem = foodItemRepository.findById(itemRequest.getFoodItemId())
                    .orElseThrow(() -> new ResourceNotFoundException("Food item not found with id: " + itemRequest.getFoodItemId()));

            if (!foodItem.isAvailable()) {
                throw new BadRequestException("Food item is not available: " + foodItem.getName());
            }

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setFoodItem(foodItem);
            orderItem.setFoodName(foodItem.getName());
            orderItem.setUnitPrice(foodItem.getPrice());
            orderItem.setQuantity(itemRequest.getQuantity());

            orderItems.add(orderItem);

            total = total.add(foodItem.getPrice().multiply(BigDecimal.valueOf(itemRequest.getQuantity())));
        }

        order.setItems(orderItems);
        order.setTotalAmount(total);

        orderRepository.save(order);
        return toResponse(order);
    }

    public List<OrderResponse> getOrdersByUser(Long userId) {
        return orderRepository.findByUserIdOrderByCreatedAtDesc(userId).stream()
                .map(this::toResponse)
                .toList();
    }

    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    public OrderResponse getById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
        return toResponse(order);
    }

    public OrderResponse updateStatus(Long id, OrderStatus status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
        order.setStatus(status);
        orderRepository.save(order);
        return toResponse(order);
    }

    private OrderResponse toResponse(Order order) {
        List<OrderItemResponse> itemResponses = order.getItems().stream()
                .map(oi -> new OrderItemResponse(oi.getId(), oi.getFoodName(), oi.getUnitPrice(), oi.getQuantity()))
                .toList();

        return new OrderResponse(
                order.getId(),
                order.getUser().getId(),
                order.getUser().getFullName(),
                order.getDeliveryAddress(),
                order.getStatus(),
                order.getTotalAmount(),
                order.getPaymentMethod(),
                order.getCreatedAt(),
                itemResponses
        );
    }
}
