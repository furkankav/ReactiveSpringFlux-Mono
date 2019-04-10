package ffs.example;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

public class FluxParallelCall {

	@Test
	public void tranformUsingFlatMap() {

		Flux<String> stringFlux = Flux.fromIterable(Arrays.asList("A", "B", "C", "D", "E", "F")) // A, B, C, D, E, F
				.flatMap(s -> {
					return Flux.fromIterable(convertToList(s)); // A -> List[A, newValue] , B -> List[B, newValue]
				})// db or external service call that returns a flux -> s -> Flux<String>
				.log();

		StepVerifier.create(stringFlux).expectNextCount(12).verifyComplete();
	}

	@Test
    public void tranformUsingFlatMap_usingparallel(){

		//parallel and runon is to make calls parallel
		//sequential is to make parallel flux to normal flux
    	Flux<String> stringFlux = Flux.fromIterable(Arrays.asList("A","B","C","D","E","F")) // A, B, C, D, E, F
                .parallel()
                .runOn(Schedulers.parallel())
    			.flatMap(s -> Flux.fromIterable(convertToList(s)) // A -> List[A, newValue] , B -> List[B, newValue]
                )//db or external service call that returns a flux -> s -> Flux<String>
    			.sequential()
                .log();
               
        StepVerifier.create(stringFlux)
                .expectNextCount(12)
                .verifyComplete();
    }

	private List<String> convertToList(String s) {

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return Arrays.asList(s, "newValue");
	}

}
