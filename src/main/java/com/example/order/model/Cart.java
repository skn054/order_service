package com.example.order.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Cart extends BaseModel{
	
	@Column(nullable = false)
	  private Long userId;
	 
	 @OneToMany(mappedBy = "cart",cascade = CascadeType.ALL)
	 private List<CartItem> cartItems = new ArrayList<>();
	 
	 
	 public void addItem(CartItem item) {
		 this.cartItems.add(item);
		 item.setCart(this);
	 }
	 
	 public void removeItem(CartItem item) {
		 this.cartItems.remove(item);
		 item.setCart(null);
	 }
	 
	

}
