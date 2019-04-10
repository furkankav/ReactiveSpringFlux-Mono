package ffs.controller;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

import ffs.model.Book;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureWebTestClient
public class LibraryControllerTest {

	
    @Autowired
    WebTestClient client;


    @Test
    public void getAllBooksShouldReturnBook() {
        client
            .get()
            .uri("/books")
            .exchange()
            .expectStatus().isOk()
            .expectBodyList(Book.class)
            .hasSize(3);
       
        
        /*Flux<Book> response = client
        .get()
        .uri("/books")
        .exchange()
        .expectStatus().isOk()
        .returnResult(Book.class)
        .getResponseBody();
    
    	StepVerifier.create(response);*/
    }
    
    @Test
    public void getAllBooksWithEntityExchange() {
    	List<Book> temp = new ArrayList<>();
    	temp.add(new Book("c97136a4-b3a5-4d60-be70-3cb918c755a4", "Academic journals", "Springer Nature"));
    	temp.add(new Book("f6d5f937-cf66-4412-8d51-40073849f1c6", "Calypso", "Hachette Livre"));
    	temp.add(new Book("2671b0df-25a9-47d0-b4f4-ee78f90ba7f5", "Test", "Penguin Random House"));
    	List<Book> expected = temp; 
    	
    	EntityExchangeResult<List<Book>> actual = client
            .get()
            .uri("/books")
            .exchange()
            .expectStatus().isOk()
            .expectBodyList(Book.class)
            .returnResult();
    	
    	assertEquals(expected, actual.getResponseBody());
    	
    	
    }
    
	/*
	 * @Test public void fluxStream(){
	 * //In case response is MediaType.APPLICATION_STREAM_JSON_VALUE and infinite
	 * Flux<Long> longStreamFlux = webTestClient
	 * .get()
	 * .uri("/fluxstream")
	 * .accept(MediaType.APPLICATION_STREAM_JSON) 
	 * .exchange() 
	 * .expectStatus()
	 * .isOk()
	 * .returnResult(Long.class) 
	 * .getResponseBody();
	 * 
	 * StepVerifier.create(longStreamFlux) .expectNext(0l) .expectNext(1l)
	 * .expectNext(2l) .thenCancel() .verify();
	 * }
	 */

}
