package io.iamkyu;

import io.iamkyu.domain.Image;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class ImageService {
    public static final String UPLOAD_ROOT = "upload-dir";

    private final ResourceLoader resourceLoader;

    public ImageService(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public Flux<Image> getAll() {
        try {
            return Flux.fromIterable(Files.newDirectoryStream(Paths.get(UPLOAD_ROOT)))
                    .map(path -> new Image(Integer.toString(path.hashCode()), path.getFileName().toString()));
        } catch (IOException e) {
            return Flux.empty();
        }
    }

    public Mono<Resource> getOne(String filename) {
        return Mono.fromSupplier(() -> resourceLoader.getResource(String.format("file:%s/%s", UPLOAD_ROOT, filename)));
    }

    public Mono<Void> create(Flux<FilePart> files) {
        return files.flatMap(file -> file.transferTo(Paths.get(UPLOAD_ROOT, file.filename()).toFile())).then();
    }

    public Mono<Void> delete(String filename) {
        return Mono.fromRunnable(() -> {
            try {
                Files.deleteIfExists(Paths.get(UPLOAD_ROOT, filename));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
