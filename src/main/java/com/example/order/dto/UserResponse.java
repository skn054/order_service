package com.example.order.dto;



import java.util.List;
import java.util.stream.Collectors;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {
	
	
	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	private List<AddressDto> addresses;
	
	

}
