package io.iamkyu.techio;

import io.iamkyu.techio.domain.User;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.function.Supplier;

public class Part03StepVerifier {
    // Use StepVerifier to check that the flux parameter emits "foo" and "bar" elements then completes successfully.
    void expectFooBarComplete(Flux<String> flux) {
        StepVerifier.create(flux)
                .expectNext("foo", "bar")
                .verifyComplete();
    }

    // Use StepVerifier to check that the flux parameter emits "foo" and "bar" elements then a RuntimeException error.
    void expectFooBarError(Flux<String> flux) {
        StepVerifier.create(flux)
                .expectError();
    }

    // Use StepVerifier to check that the flux parameter emits a User with "swhite"username
    // and another one with "jpinkman" then completes successfully.
    void expectSkylerJesseComplete(Flux<User> flux) {
        StepVerifier.create(flux)
                .assertNext(u -> "swhite".equals(u.getUsername()))
                .assertNext(u -> "jpinkman".equals(u.getUsername()))
                .verifyComplete();
    }

    // Expect 10 elements then complete and notice how long the test takes.
    void expect10Elements(Flux<Long> flux) {
        StepVerifier.create(flux)
                .expectNextCount(10)
                .verifyComplete();
    }

    // Expect 3600 elements at intervals of 1 second, and verify quicker than 3600s
    // by manipulating virtual time thanks to StepVerifier#withVirtualTime, notice how long the test takes
    void expect3600Elements(Supplier<Flux<Long>> supplier) {
        StepVerifier
                .withVirtualTime(supplier)
                .expectSubscription()
                .thenAwait(Duration.ofHours(1))
                .expectNextCount(3600)
                .expectComplete()
                .verify();
    }
}
