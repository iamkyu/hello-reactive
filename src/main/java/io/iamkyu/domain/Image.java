package io.iamkyu.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Image {
    private String id;
    private String name;

    public Image(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Image{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
