package com.alt.controller;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alt.consumer.BookConsumer;
import com.alt.consumer.PaymentConsumer;
import com.alt.model.Book;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/student")
public class StudentController {

	Logger logger = LoggerFactory.getLogger(StudentController.class);
	
	@Autowired
	BookConsumer bookConsumer;
	
	@Autowired
	PaymentConsumer paymentConsumer;
	
//	@Value("${my.app.title}")
//	private String title;
	
	

//	public ResponseEntity<List<Book>> getAllBook(){
//		List<Book> allBook = bookConsumer.getAllBook();
//		String  paymentData =  paymentConsumer.getPaymentData();
//		System.out.println(paymentData);
//		//System.out.println("title::: " + title);
//		return new ResponseEntity<List<Book>>(allBook,HttpStatus.OK);
//	}
    @Operation(
            summary = "Get All Book REST API",
            description = "REST API to get All details of Book"
    )
	@GetMapping("/getAllBook")
	@RateLimiter(name = "getBookRateLimiter",fallbackMethod = "getAllBookFallBack")
	@Bulkhead(name = "getBookBulkhead",fallbackMethod = "getAllBookFallBack")
	@Retry(name = "getBookDataRetry",fallbackMethod = "getAllBookDataFallBack")
	@CircuitBreaker(name = "getBookDataCircuiteBreaker",fallbackMethod = "getAllBookDataFallBack")
	public ResponseEntity<String> getAllBook() throws IOException{
		List<Book> allBook = bookConsumer.getAllBook();
		String  paymentData =  paymentConsumer.getPaymentData();
		System.out.println(paymentData);
		return new ResponseEntity<String>(allBook.toString(),HttpStatus.OK);
	}

	
	public ResponseEntity<String> getAllBookFallBack(java.lang.Throwable throwable){
		logger.info("Rate Limiter is applied");
		return  ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body("Too many requests :: No further request will be accepted. Please try after sometime");
	}
	
	public ResponseEntity<String> getAllBookDataFallBack(java.lang.Throwable throwable) {
		logger.info("Fall back method of Retry");
		return  ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Service is Down :: Please try after sometime");

	}
	
    @Operation(
            summary = "Get Book By Id REST API",
            description = "REST API to get Book details Based on Id"
    )
	@GetMapping("/getBook/{id}")
	@HystrixCommand(fallbackMethod = "HyStrixFallBackMethod" , 
	            commandProperties =  {
	            		@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",
	            				         value = "6"),
	            		@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",
				                         value = "10000"),
	            		@HystrixProperty(name = "circuitBreaker.enabled",
				                         value = "false"),
	            } )
	
	public String getBookById(@PathVariable Integer id) {
		Book book = bookConsumer.getBookId(id);
		return book.getBookName();
	}
	
	
	public String HyStrixFallBackMethod() {
		System.out.println("---FROM FALLBACK METHOD---");
		return "SERVICE IS DOWN, PLEASE TRY AFTER SOMETIME !!!";
	}
	
	   @Operation(
	            summary = "Get Entity REST API",
	            description = "REST API to get Entity Details"
	    )
	@GetMapping("/get/entityData")
	public String printEntityData() {
		ResponseEntity<String> res =  bookConsumer.getEntityData();
		String response =  res.getBody();
		return response; 
	}
	
}
