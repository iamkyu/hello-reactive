package io.iamkyu;

import io.iamkyu.domain.Image;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Hooks;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
public class ImageRestController {
    private static final String API_BASE_PATH = "/api";

    @GetMapping(API_BASE_PATH + "/images")
    public Flux<Image> images() {
        Hooks.onOperatorDebug();

        return Flux.just(
                new Image("1", "larning-spring-boot-cover.jpg"),
                new Image("2", "learning-spring-boot-2nd-edition-cover.jpg"),
                new Image("3", "bazinga.png")
        );
    }

    @PostMapping(API_BASE_PATH + "/images")
    public Mono<Void> create(@RequestBody Flux<Image> images) {
        return images
                .map(image -> {
                    log.info(String.format("We will save %s to a Reactive database soon!", image));
                    return image;
                })
                .then();
    }
}
