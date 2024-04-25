package com.alt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alt.consumer.CartRestConsumer;

@RestController
@RequestMapping("/payment")
public class PaymentController {

	@Autowired
	CartRestConsumer restConsumer;
	
	@GetMapping(value ="/getData")
	public String getPaymentData() {
		String cartData = restConsumer.getCartInfo();
		System.out.println("cartData::::"+ cartData);
		return cartData;
	}
	
}
