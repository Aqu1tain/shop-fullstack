package com.shop.backend.dto;

import com.shop.backend.entity.OrderStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class OrderDTO {
    private Long id;
    private Long userId;
    private String username;
    private Long productId;
    private String productName;
    private Integer quantity;
    private LocalDateTime orderDate;
    private BigDecimal totalAmount;
    private OrderStatus status;
}
