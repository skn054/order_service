package com.example.order.dto;

import java.math.BigDecimal;

import com.example.order.model.CartItem;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemDto {
	
	private Long productId;
	private Long quantity;
	 private String productName;
	 private BigDecimal price;
	
	public static CartItemDto mapToCartItemDto(CartItem cartItem) {
		
		CartItemDto cartItemDto = CartItemDto.builder()
				.productId(cartItem.getProductId())
				.productName(cartItem.getProductName())
				.price(cartItem.getPrice())
				.quantity(cartItem.getQuantity())
				.build();
		return cartItemDto;
		
	}

}
