package io.iamkyu.techio;

import io.iamkyu.techio.domain.ReactiveRepository;
import io.iamkyu.techio.domain.ReactiveUserRepository;
import io.iamkyu.techio.domain.User;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class Part06Request {

    ReactiveRepository<User> repository = new ReactiveUserRepository();


    // Create a StepVerifier that initially requests all values and expect 4 values to be received
    StepVerifier requestAllExpectFour(Flux<User> flux) {
        return StepVerifier.create(flux)
                .expectNextCount(4)
                .expectComplete();
    }


    // Create a StepVerifier that initially requests 1 value and expects User.SKYLER then requests another value and expects User.JESSE.
    StepVerifier requestOneExpectSkylerThenRequestOneExpectJesse(Flux<User> flux) {
        return StepVerifier.create(flux)
                .expectNext(User.SKYLER)
                .expectNext(User.JESSE)
                .thenCancel();
    }


    // Return a Flux with all users stored in the repository that prints automatically logs for all Reactive Streams signals
    Flux<User> fluxWithLog() {
        return Flux.from(repository.findAll())
                .log();
    }


    // Return a Flux with all users stored in the repository that prints "Starring:" on subscribe, "firstname lastname" for all values and "The end!" on complete
    Flux<User> fluxWithDoOnPrintln() {
        return Flux.from(repository.findAll())
                .doOnSubscribe(s -> System.out.println("Starring:"))
                .doOnNext(u -> System.out.println(u.getFirstname() + " " + u.getLastname()))
                .doOnComplete(() -> System.out.println("The end!"));
    }

}
