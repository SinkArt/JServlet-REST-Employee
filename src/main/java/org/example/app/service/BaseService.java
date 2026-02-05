package org.example.app.service;


import java.util.List;

public interface BaseService<T,S> {
    // Створення нового запису
    T create(S request);
    // Отримання всіх записів
    List<T> getAll();

    // ---- Path Params ----------------------

    // Отримання запису за id
    T getById(Long id);
    // Оновлення запису за id
    T update(Long id, S request);
    // Видалення запису за id
    boolean deleteById(Long id);
}
