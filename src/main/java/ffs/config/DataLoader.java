package ffs.config;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import ffs.model.Book;
import ffs.repository.BookRepository;
import lombok.extern.java.Log;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Log
@Component
public class DataLoader implements ApplicationRunner {
	@Autowired
	private BookRepository bookRepository;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		Flux<Book> check = bookRepository.findAll();
		List<Mono<Book>> savedBooks = new ArrayList<>();
		if (check.count().block() <= 0) {
			savedBooks.add(bookRepository.save(new Book(UUID.randomUUID().toString(), "Test", "Penguin Random House")));
			savedBooks.add(bookRepository.save(new Book(UUID.randomUUID().toString(), "Calypso", "Hachette Livre")));
			savedBooks.add(bookRepository.save(new Book(UUID.randomUUID().toString(), "Academic journals", "Springer Nature")));
		}
		Mono.when(savedBooks).subscribe();
		log.info("Book count is " + check.count().block().toString());
	}

}
