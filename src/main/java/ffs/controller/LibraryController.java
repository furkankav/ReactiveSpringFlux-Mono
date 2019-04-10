package ffs.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ffs.model.Book;
import ffs.service.BookService;
import ffs.service.KafkaProducer;
import reactor.core.publisher.Flux;

@RestController
public class LibraryController {

	@Autowired
	BookService bookService;
	
	@Autowired
	KafkaProducer kafkaProducer;

	@GetMapping("books")
	public ResponseEntity<Flux<Book>> getAllBooks() {
		Flux<Book> books = bookService.getAllBooks();
		return ResponseEntity.ok(books);
		//return new ResponseEntity<Flux<Book>>(books, HttpStatus.CREATED);
	}

	@PostMapping("book")
	public ResponseEntity<?> addBook(@Valid @RequestBody Book book){
		bookService.saveBook(book);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@GetMapping(value = "fluxstream/books", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
	public ResponseEntity<Flux<Book>> getAllBooksStream() {
		Flux<Book> books = bookService.getAllBooks();
		return ResponseEntity.ok(books);
		//return new ResponseEntity<Flux<Book>>(books, HttpStatus.CREATED);
	}
	
	@GetMapping("message/books")
	public ResponseEntity<?> sendBooksToKafka() {
		Flux<Book> books = bookService.getAllBooks();
		books.subscribe(book -> kafkaProducer.sendMessage(book.getTitle()));
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
	
}
