package org.example.app.dto.employee;

import jakarta.servlet.http.HttpServletResponse;
import org.example.app.entity.Employee;

public record EmployeeDtoUpdateResponse(
        int statusCode,
        boolean success,
        String message,
        Employee employee) {

    public static final String SUCCESS_MESSAGE = "Employee with id %s has been updated successfully.";
    public static final String FAILURE_MESSAGE = "Employee with id %s has not been found!";

    public static EmployeeDtoUpdateResponse of(Long id, boolean isEmployeeFound, Employee employeeUpdated) {
        if (isEmployeeFound)
            return new EmployeeDtoUpdateResponse(
                    HttpServletResponse.SC_OK,
                    true, SUCCESS_MESSAGE.formatted(id), employeeUpdated);
        else
            return new EmployeeDtoUpdateResponse(
                    HttpServletResponse.SC_NOT_FOUND,
                    false, FAILURE_MESSAGE.formatted(id), null);
    }
}
