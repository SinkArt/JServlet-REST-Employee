package org.example.app.dto.employee;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Java records - клас, мета якого зберігати дані та надавати
 * деякі спеціальні функції, наприклад, немає необхідності описувати
 * конструктор, геттер, equals, hashCode, оскільки в рекорді ці речі будуть
 * автоматично згенеровані компілятором
 */
// @JsonIgnoreProperties допомогає уникнути помилки
// при створенні об'єкту цього типу якщо якесь поле є null
@JsonIgnoreProperties(ignoreUnknown = true)
public record EmployeeDtoRequest(
        Long id,
        String firstName,
        String position,
        String telephone) {
}
