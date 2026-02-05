package org.example.app.repository;


import java.util.List;
import java.util.Optional;

public interface BaseRepository<T,S> {
    // Створення нового запису
    void save(S request);
    // Отримання всіх записів
    Optional<List<T>> getAll();

    // ---- Path Params ----------------------

    // Отримання запису за id
    Optional<T> getById(Long id);
    // Оновлення запису за id
    void update(Long id, S request);
    // Видалення запису за id
    boolean deleteById(Long id);
}
