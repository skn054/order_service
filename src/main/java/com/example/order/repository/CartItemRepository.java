package com.example.order.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.order.model.Cart;
import com.example.order.model.CartItem;



@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long>{
	
	public Optional<CartItem> findByCartAndProductId(Cart cart,Long productId);

	
}
