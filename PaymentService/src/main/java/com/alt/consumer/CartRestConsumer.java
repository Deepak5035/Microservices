package com.alt.consumer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class CartRestConsumer {

	@Autowired
	DiscoveryClient client;

	public String getCartInfo() {

		List<ServiceInstance> serviceInstanceList = client.getInstances("CART-SERVICE");
		ServiceInstance serviceInstance = serviceInstanceList.get(0);
		String url = serviceInstance.getUri() + "/cart/getData";
		RestTemplate rt = new RestTemplate();
		String response = rt.getForObject(url, String.class);
		return response;

	}
}
