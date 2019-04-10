package ffs.example;

import org.junit.Test;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class FluxAndMonoTest {
	
	//There is cold hot reactive streams
	//There is virtual time to speed up unit tests in functions that use delays

	@Test
	public void fluxTest() {
		// creating flux
		Flux<String> stringFlux = Flux.just("Spring", "Spring Boot", "Reactive Spring")
				.concatWith(Flux.error(new RuntimeException("Exception Occured")));
				//.log();
		// Consuming flux
		stringFlux.subscribe(System.out::println,
				error -> System.err.println(error),
				() -> System.out.println("Flux is completed"));
	}
	
	@Test
	public void fluxTestElements_WithError() {
		// creating flux
		Flux<String> stringFlux = Flux.just("Spring", "Spring Boot", "Reactive Spring")
				.concatWith(Flux.error(new RuntimeException("Exception Occured")));
				//onErrorMap(e -> new CustomeException(e)) you can map error to custom exception
				//onErrorReturn("text") you can return some text error that will be in flux/mono
				//onErrorReusme(e -> ...... return Flux....) you can do something with error and return some flux
				//retry(2) if you encounter error it tries to run again before throwing exception
				//retryBackoff(2, Duration.ofSeconds(5)) it wait 5 sec before it tries again
				//.log();
		// testing flux you need to end your test with verify call
		StepVerifier.create(stringFlux)	
			.expectNext("Spring")
			.expectNext("Spring Boot")
			.expectNext("Reactive Spring")
			//if there was no error
			//.verifyComplete();
			//you can expect exception class or text
			//.expectError(RuntimeException.class)
			.expectErrorMessage("Exception Occured")
			.verify();
		
	}
	
	@Test
	public void fluxTestElementsCount_WithError() {
		// creating flux
		Flux<String> stringFlux = Flux.just("Spring", "Spring Boot", "Reactive Spring")
				.concatWith(Flux.error(new RuntimeException("Exception Occured")));
				//.log();
		// testing flux you need to end your test with verify call
		StepVerifier.create(stringFlux)	
			.expectNextCount(3)
			.expectErrorMessage("Exception Occured")
			.verify();
		
	}

}
