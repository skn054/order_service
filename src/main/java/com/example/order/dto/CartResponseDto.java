package com.example.order.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.example.order.model.Cart;

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
public class CartResponseDto {
	
	private Long userId;
	
	private List<CartItemDto> cartItems;
	
	
	public static CartResponseDto maptoCartResponseDto(Cart cart) {
		CartResponseDto responseDto = CartResponseDto
				.builder()
				.userId(cart.getUserId())
				.cartItems(cart.getCartItems().stream().map(CartItemDto::mapToCartItemDto).collect(Collectors.toList()))
				.build();
		
		
		return responseDto;
	}

}
