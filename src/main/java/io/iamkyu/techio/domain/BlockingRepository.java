package io.iamkyu.techio.domain;

public interface BlockingRepository<T> {
    void save(T value);

    T findFirst();

    Iterable<T> findAll();

    T findById(String id);
}
