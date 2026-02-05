package org.example.app.service.employee;

import org.example.app.dto.employee.EmployeeDtoRequest;
import org.example.app.entity.Employee;
import org.example.app.service.BaseService;

import java.util.List;

public interface EmployeeService extends BaseService<Employee, EmployeeDtoRequest> {
    // Створення нового запису
    Employee create(EmployeeDtoRequest request);
    // Отримання всіх записів
    List<Employee> getAll();

    // ---- Path Params ----------------------

    // Отримання запису за id
    Employee getById(Long id);
    // Оновлення запису за id
    Employee update(Long id, EmployeeDtoRequest request);
    // Видалення запису за id
    boolean deleteById(Long id);
}
