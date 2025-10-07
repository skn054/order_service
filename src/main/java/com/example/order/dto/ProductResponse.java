package com.example.order.dto;


import java.math.BigDecimal;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse{
	
	
	private Long id;
	
	private String name;
	
	private String description;
	
	
	private Long stockQuantity;
	
	
	private BigDecimal price;
	
	
	private String categoryName;
	
	
	

	

}
