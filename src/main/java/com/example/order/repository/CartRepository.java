package com.example.order.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.order.model.Cart;



@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

	public Optional<Cart> findByUserId(Long id);
	
	
	
	
}
