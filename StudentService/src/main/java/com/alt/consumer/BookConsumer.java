package com.alt.consumer;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.alt.model.Book;

@FeignClient(name = "BOOK-SERVICE")
public interface BookConsumer {

	@GetMapping("book/{id}")
	public Book getBookId(@PathVariable Integer id);
	
	@GetMapping("book/all")
	public List<Book> getAllBook();

	@GetMapping("book/entity")
	public ResponseEntity<String> getEntityData();
}
