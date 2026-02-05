package org.example.app.dto.employee;

import jakarta.servlet.http.HttpServletResponse;
import org.example.app.entity.Employee;

public record EmployeeDtoCreateResponse(
        int statusCode,
        boolean success,
        String message,
        Employee employee) {

    public static final String SUCCESS_MESSAGE = "Employee has been created successfully.";
    public static final String FAILURE_MESSAGE = "Employee has not been created!";

    public static EmployeeDtoCreateResponse of(boolean isEmployeeCreated, Employee employee) {
        if (isEmployeeCreated)
            return new EmployeeDtoCreateResponse(
                    HttpServletResponse.SC_OK,
                    true, SUCCESS_MESSAGE, employee);
        else
            return new EmployeeDtoCreateResponse(
                    HttpServletResponse.SC_NO_CONTENT,
                    false, FAILURE_MESSAGE, null);
    }
}
