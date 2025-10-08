package com.example.order.service;



import jakarta.transaction.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.example.order.dto.OrderResponseDto;
import com.example.order.dto.ProductResponse;
import com.example.order.dto.UserResponse;
import com.example.order.exception.InsufficientStockException;
import com.example.order.exception.ResourceNotFoundException;
import com.example.order.model.Cart;
import com.example.order.model.Order;
import com.example.order.model.OrderItem;
import com.example.order.model.OrderStatus;
import com.example.order.repository.CartRepository;
import com.example.order.repository.OrderRepository;

@Service
public class OrderService {

    private final RestTemplate restTemplate;

    private final OrderRepository orderRepository;

    private final CartRepository cartRepository;

   


    OrderService(CartRepository cartRepository, OrderRepository orderRepository, RestTemplate restTemplate) {
        
        this.cartRepository = cartRepository;
        this.orderRepository = orderRepository;
        this.restTemplate = restTemplate;
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
    
    
   
	
	
    
    @Transactional
	public OrderResponseDto placeOrder(Long userId) {
		
		// get cart for user. if cart not presentthrow wxception.
		// get items from cart and cteate order itmes and persists in db.
		// calcuate total order value and create a new order and
		
    	
    	
    	
    	
    	UserResponse user = getUser(userId);
    	
    	
		
		Cart cart = cartRepository.findByUserId(user.getId()).orElseThrow(()-> new ResourceNotFoundException("Cart Empty for user with id"+ userId));
		
		Order order = Order.builder()
				.userId(user.getId())
				.shippingCity(user.getAddresses().get(0).getCity())
				.shippingState(user.getAddresses().get(0).getState())
				.shippingStreet(user.getAddresses().get(0).getStreet())
				.shippingZipcode(user.getAddresses().get(0).getZipcode())
				.status(OrderStatus.PENDING)
				.build();
		
		List<OrderItem>  orderItems =  cart.getCartItems().stream().map( item->{
			
			
			ProductResponse product =     getProduct(item.getProductId());
//			Product product = item.getProduct();
			
			if (product.getStockQuantity() < item.getQuantity()) {
	            throw new InsufficientStockException("Not enough stock for " + product.getName());
	        }
			
			long newStock = product.getStockQuantity() - item.getQuantity();
//			product.setStockQuantity(newStock);  // update product stock using network call.
			
			String productUrl = "http://product-service/api/products/" + product.getId() 
								+ "/stock?quantity=" + item.getQuantity();
			
			restTemplate.patchForObject(productUrl, null, Void.class);
			
			/// No need to call productRepository.save(product) here!
		    // Because the method is @Transactional, Hibernate's "dirty checking"
		    // will automatically detect the change to the product entity
		    // and include it in the final transaction commit.
			
			
			return OrderItem.builder()
					.productId(product.getId())
					.productName(product.getName())
					.quantity(item.getQuantity())
					.price(product.getPrice())
					.build();
			
			
		}).collect(Collectors.toList());
		
		
		//build two way relation
		orderItems.forEach(order::addOrderItem);
		
		
		
		
		//calculate order price;
		BigDecimal totalPrice =  order.getOrderItems().stream()
						.map(item ->  item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
						.reduce(BigDecimal.ZERO,(a,b)->a.add(b));
				
		order.setTotalAmount(totalPrice);
		Order placedOrder = orderRepository.save(order);
		
		cartRepository.delete(cart);
		
		return OrderResponseDto.mapToOrderDto(order);
		
		
		
	}

	private ProductResponse getProduct(Long productId) {
		String productUrl = "http://product-service/api/products/" + productId;
		try {
			return restTemplate.getForObject(productUrl, ProductResponse.class);
		}
		catch(HttpClientErrorException.NotFound ex) {
			throw new ResourceNotFoundException("Product not found with id: " + productId);
		}
		
	}

}
