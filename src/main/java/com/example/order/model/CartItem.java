package com.example.order.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItem extends BaseModel{
	
	
	@ManyToOne
	@JoinColumn(name = "cart_id")
	private Cart cart;
	
	
	@Column(nullable = false)
	private Long productId;
	
	private String productName;
	
	private Long quantity;
	
	private BigDecimal price;
	
	
	

}
