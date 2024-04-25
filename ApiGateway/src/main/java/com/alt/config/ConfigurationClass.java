package com.alt.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigurationClass {

	@Bean
	public RouteLocator ConfigureRoute(RouteLocatorBuilder builder) {
		
		return builder.routes()
				.route("book", r->r.path("/book/**")
				.uri("http://localhost:9000")) 
				.route("student", r->r.path("/student/**")
				.uri("lb://STUDENT-SERVICE"))
				.build();
		
	}
	
}
