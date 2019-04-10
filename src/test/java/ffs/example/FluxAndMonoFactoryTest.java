package ffs.example;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import org.junit.Test;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class FluxAndMonoFactoryTest {

	List<String> names = Arrays.asList("adam", "anna", "jack", "jenny");

	@Test
	public void fluxUsingIterable() {

		// Creating flux from a list
		Flux<String> namesFlux = Flux.fromIterable(names);
		// you can also use Flux.fromStream or Flux.fromArray
		// there is also Flux.range(1,5) to create integer flux 1,2,3,4,5
		StepVerifier.create(namesFlux)
			.expectNext("adam", "anna", "jack", "jenny")
			.verifyComplete();
	}

	//Flux.merge(flux1, flux2) this is to merge fluxes
	//Flux.concat(flux1, flux2) this is to merge fluxes but in a sequential manner
	@Test
	public void monoUsingJustOrEmpty() {
		Mono<String> mono = Mono.justOrEmpty(null); // Mono.Empty();
		StepVerifier.create(mono.log())
			.verifyComplete();
	}
	
	@Test
	public void monoUsingSupplier() {
		
		Supplier<String> stringSupplier = () -> "adam";
		//you can get adam by stringSupplier.get() as well
		Mono<String> mono = Mono.fromSupplier(stringSupplier); // Mono.Empty();
		StepVerifier.create(mono.log())
			.expectNext("adam")
			.verifyComplete();
	}

}
