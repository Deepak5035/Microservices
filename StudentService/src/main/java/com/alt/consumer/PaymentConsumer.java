package com.alt.consumer;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "PAYMENT-SERVICE")
public interface PaymentConsumer {

	@GetMapping(value ="/payment/getData")
	public String getPaymentData();
	
}
