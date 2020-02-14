package io.iamkyu.techio;

import io.iamkyu.techio.domain.User;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;

public class Part09Adapt {
    // Adapt Flux to RxJava Flowable
    Flowable<User> fromFluxToFlowable(Flux<User> flux) {
        return Flowable.fromPublisher(flux);
    }

    // Adapt RxJava Flowable to Flux
    Flux<User> fromFlowableToFlux(Flowable<User> flowable) {
        return Flux.from(flowable);
    }

    // Adapt Flux to RxJava Observable
    Observable<User> fromFluxToObservable(Flux<User> flux) {
        return Observable.fromPublisher(flux);
    }

    // Adapt RxJava Observable to Flux
    Flux<User> fromObservableToFlux(Observable<User> observable) {
        return Flux.from(observable.toFlowable(BackpressureStrategy.BUFFER));
    }

    // Adapt Mono to RxJava Single
    Single<User> fromMonoToSingle(Mono<User> mono) {
        return Single.fromPublisher(mono);
    }

    // Adapt RxJava Single to Mono
    Mono<User> fromSingleToMono(Single<User> single) {
        return Mono.from(single.toFlowable());
    }

    // Adapt Mono to Java 8+ CompletableFuture
    CompletableFuture<User> fromMonoToCompletableFuture(Mono<User> mono) {
        return mono.toFuture();
    }

    // Adapt Java 8+ CompletableFuture to Mono
    Mono<User> fromCompletableFutureToMono(CompletableFuture<User> future) {
        return Mono.fromFuture(future);
    }
}
