package com.example.order.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.order.dto.OrderResponseDto;
import com.example.order.service.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;


    OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
	
	
	@PostMapping
	public ResponseEntity<OrderResponseDto> placeOrder(@RequestHeader("X-User-ID") Long userId) {
		
	 	 return ResponseEntity.status(HttpStatus.CREATED).body(orderService.placeOrder(userId));
		
	}

}
