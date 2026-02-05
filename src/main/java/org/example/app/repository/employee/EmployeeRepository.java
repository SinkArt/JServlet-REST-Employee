package org.example.app.repository.employee;


import org.example.app.dto.employee.EmployeeDtoRequest;
import org.example.app.entity.Employee;
import org.example.app.repository.BaseRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends BaseRepository<Employee, EmployeeDtoRequest> {
    // Створення нового запису
    void save(EmployeeDtoRequest request);
    // Отримання всіх записів
    Optional<List<Employee>> getAll();

    // ---- Path Params ----------------------

    // Отримання запису за id
    Optional<Employee> getById(Long id);
    // Оновлення запису за id
    void update(Long id, EmployeeDtoRequest request);
    // Видалення запису за id
    boolean deleteById(Long id);

    // ---- Utils ----------------------

    // Отримання останнього запису
    Optional<Employee> getLastEntity();

}
