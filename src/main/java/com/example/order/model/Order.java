package com.example.order.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.ManyToAny;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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
@Table(name = "orders")
public class Order extends BaseModel{
	
	
	
	 @Column(nullable = false)
	   private Long userId;
	
	
	 @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
	 @Builder.Default
	 private List<OrderItem> orderItems = new ArrayList<>();
	 
	 
	 private String shippingStreet;
	 private String shippingCity;
	 private String shippingState;
	 private String shippingZipcode;
	 private String shippingCountry;
	 
	 private BigDecimal totalAmount; 
	 
	 @Enumerated(EnumType.STRING)
	 private OrderStatus status;
	 
	 
	 public void addOrderItem(OrderItem item) {
		 this.orderItems.add(item);
		 item.setOrder(this);
	 }
	 
	 public void deleteOrderItem(OrderItem item) {
		 this.orderItems.remove(item);
		 item.setOrder(null);
	 }
	 
	 
	 
	

}
