package com.example.order.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
public class OrderItem extends BaseModel{
	
	 @ManyToOne
	 @JoinColumn(name = "order_id")
	 @JsonIgnore
	 private Order order;
	 
	 @Column(nullable = false)
	  private Long productId;
	 
	 private Long quantity;
	 
	 private String productName;
	 
	 private BigDecimal price; 

}
