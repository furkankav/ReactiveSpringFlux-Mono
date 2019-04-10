package ffs.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ffs.model.Book;
import ffs.repository.BookRepository;
import reactor.core.publisher.Flux;

@Service
public class BookService {

	@Autowired
	BookRepository bookRepository;

	public Flux<Book> getAllBooks() {
		return bookRepository.findAll();
	}

	public void saveBook(Book book) {
		String id = UUID.randomUUID().toString();
		book.setId(id);
		bookRepository.save(book).subscribe();
	}

}
