package com.alt.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alt.model.Book;

@RestController
@RequestMapping("/book")
public class BookController {

	@GetMapping("/{id}")
	public Book getBookId(@PathVariable Integer id) {
		return new Book(id, "Head First Java", 500.75);
	}

	@GetMapping("/all")
	public List<Book> getAllBook() {
		return List.of(new Book(501, "Head First Java", 439.75), new Book(502, "Spring in Action", 340.75),
				new Book(503, "Hibernate in Action", 355.75));
	}

	@GetMapping("/entity")
	public ResponseEntity<String> getEntityData() {
		return new ResponseEntity<String>("Hello from BookRestController", HttpStatus.OK);
	}
}
