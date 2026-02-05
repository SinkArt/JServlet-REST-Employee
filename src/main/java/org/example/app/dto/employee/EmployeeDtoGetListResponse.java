package org.example.app.dto.employee;

import jakarta.servlet.http.HttpServletResponse;
import org.example.app.entity.Employee;

import java.util.Collections;
import java.util.List;

public record EmployeeDtoGetListResponse(
        int statusCode,
        boolean success,
        String message,
        List<Employee> employeeList) {

    public static final String SUCCESS_MESSAGE = "Employee list has been fetched successfully.";
    public static final String FAILURE_MESSAGE = "Employee list has not been found!";

    public static EmployeeDtoGetListResponse of(boolean isEmployeeListEmpty, List<Employee> list) {
        if (isEmployeeListEmpty) {
            return new EmployeeDtoGetListResponse(
                    HttpServletResponse.SC_NOT_FOUND,
                    false,
                    FAILURE_MESSAGE,
                    Collections.emptyList()
            );
        } else {
            return new EmployeeDtoGetListResponse(
                    HttpServletResponse.SC_OK,
                    true,
                    SUCCESS_MESSAGE,
                    list // Використовуємо аргумент методу 'list'
            );
        }
    }
}