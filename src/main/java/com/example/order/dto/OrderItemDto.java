package com.example.order.dto;

import java.math.BigDecimal;

import com.example.order.model.OrderItem;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter 
@Setter 
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDto {
    private Long productId;
    private String productName;
    private Long quantity;
    private BigDecimal price;
    
    public static OrderItemDto mapToOrderItemDto(OrderItem item) {
    	
    	return OrderItemDto.builder().
    			productId(item.getProductId())
    			.productName(item.getProductName())
    			.quantity(item.getQuantity())
    			.price(item.getPrice())
    			.build();
    }
}
