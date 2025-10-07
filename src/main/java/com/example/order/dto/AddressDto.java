package com.example.order.dto;


import com.example.order.model.Order;

import lombok.*;

@Getter
@Setter
@Builder
public class AddressDto {
    private String street;
    private String city;
    private String state;
    private String zipcode;
    private String country;
    
    
    public static AddressDto getAddressDto(Order order) {
    	return AddressDto.builder()
    			.city(order.getShippingCity())
    			.country(order.getShippingCountry())
    			.state(order.getShippingState())
    			.zipcode(order.getShippingZipcode())
    			.street(order.getShippingStreet())
    			.build();
    }
}