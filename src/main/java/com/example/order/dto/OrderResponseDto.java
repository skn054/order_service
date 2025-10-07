package com.example.order.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.example.order.model.Order;

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
public class OrderResponseDto {
	
	private Long orderId;
	
	
	private Long userId;
	
	private String status;
	
	 private AddressDto shippingAddress;
	
	private BigDecimal totalAmount;
	
	private List<OrderItemDto> orderItemList = new ArrayList<>();
	
	public static OrderResponseDto mapToOrderDto(Order order) {
		
		
		 List<OrderItemDto> itemDtos = order.getOrderItems().stream()
		            .map(OrderItemDto::mapToOrderItemDto) // Use the existing static method
		            .collect(Collectors.toList());
		 
		 AddressDto address = AddressDto.getAddressDto(order);
//		 
		OrderResponseDto responseDto = OrderResponseDto.builder()
				.orderId(order.getId())
				.userId(order.getUserId())
				.totalAmount(order.getTotalAmount())
				.status(order.getStatus().name())
//				.shippingAddress(address)
				.orderItemList(itemDtos)
				.build();
		
		
		
		
//		responseDto.setShippingAddress(address);
		return responseDto;
				
	}
}
