package com.example.order.service;


import jakarta.transaction.Transactional;

import java.util.Arrays;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import com.example.order.dto.CartItemDto;
import com.example.order.dto.CartResponseDto;
import com.example.order.dto.ProductResponse;
import com.example.order.dto.UserResponse;
import com.example.order.exception.ResourceNotFoundException;
import com.example.order.model.Cart;
import com.example.order.model.CartItem;
import com.example.order.repository.CartItemRepository;
import com.example.order.repository.CartRepository;


@Service
public class CartService {

    private final RestTemplate restTemplate;

    private final CartRepository cartRepository;

    
    
    private final CartItemRepository cartItemRepository;

    public CartService(CartRepository cartRepository,CartItemRepository cartItemRepoistory, RestTemplate restTemplate) {
        
        this.cartRepository = cartRepository;
        
        this.cartItemRepository = cartItemRepoistory;
        
        this.restTemplate = restTemplate;
        
    }
	
	
	
    @Transactional
	public void addProductToCart(Long userId, CartItemDto cartItemDto) {
		
		//check if user exists? if doesn't throw exception. 
		// If user exists, check if cart exists for a user? If cart exists then check if product and exist. If stock exists.
		//If doesnt create a cart. 
		// check if product exists and quantity of product worth of stock exists.
		// add product to cart.
	
    	UserResponse user = getUser(userId);
    	
    	if(user == null) {
    		throw new ResourceNotFoundException("User not found with id"+ userId);
    	}
		
    	String productUrl = "http://product-service/api/products/" + cartItemDto.getProductId();
    	ProductResponse product = restTemplate.getForObject(productUrl, ProductResponse.class);
    	
    	
    	if(product== null) {
    		 throw new ResourceNotFoundException("Product not found with id"+ cartItemDto.getProductId());
    	}
		
		
		
		if(product.getStockQuantity() < cartItemDto.getQuantity()) {
			 throw new RuntimeException("Not enough stock for product: " + product.getName());
		}
		
		 Cart cart = cartRepository.findByUserId(user.getId())
	                .orElseGet(() -> {
	                    Cart newCart = Cart.builder().userId(userId).build();
	                    return cartRepository.save(newCart);
	                });
		 
		 Optional<CartItem> existingCartItemOpt = cartItemRepository.findByCartAndProductId(cart, product.getId());
		 if(existingCartItemOpt.isPresent()) {
			 
			 CartItem existingItem = existingCartItemOpt.get();
			 existingItem.setQuantity(existingItem.getQuantity() + cartItemDto.getQuantity());
			 cartItemRepository.save(existingItem);
		 }else {
			 
			 CartItem newCartItem = CartItem.builder()
					 .productId(product.getId())
					 .quantity(cartItemDto.getQuantity())
					 .cart(cart)
					 .build();
			 
			 cartItemRepository.save(newCartItem);
			 
			 
		 }
		 		
		
	}



    private UserResponse getUser(Long userId) {
		String userUrl = "http://user-service/api/users/" + userId;
		try {
			return restTemplate.getForObject(userUrl, UserResponse.class);
		}
		catch(HttpClientErrorException.NotFound ex) {
			throw new ResourceNotFoundException("User not found with id: " + userId);
		}
    	
	}
    
    public CartResponseDto getCart(Long userId) {
    	
    	
    	UserResponse user = getUser(userId);
    	if(user ==  null) {
    		throw new ResourceNotFoundException("User not found with id" + userId);
    	}
    	
    	
    	
    	Cart cart = cartRepository.findByUserId(user.getId()).orElseThrow(()->new ResourceNotFoundException("Cart not found for user with id "+ userId));
    	
    	return CartResponseDto.maptoCartResponseDto(cart);
    	
    }
    
    
    @Transactional
    public void deleteCart(Long userId) {
    	
    	UserResponse user = getUser(userId);
    	if(user ==  null) {
    		throw new ResourceNotFoundException("User not found with id" + userId);
    	}
    	
    	
    	Cart cart = cartRepository.findByUserId(user.getId()).orElseThrow(()-> new ResourceNotFoundException("Cart not found for user with id"+ userId));
    	
    	cartRepository.delete(cart);
    }

}
