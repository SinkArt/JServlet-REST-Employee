package org.example.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.app.dto.employee.*;
import org.example.app.entity.Employee;
import org.example.app.service.employee.EmployeeService;
import org.example.app.service.employee.EmployeeServiceImpl;


import java.io.IOException;
import java.util.Collections;
import java.util.List;

// має бути в /api/v1/employees/*
// для отримання ресурсу за певним id
@WebServlet("/api/v1/employees/*")
public class EmployeeController extends HttpServlet {

    private EmployeeService service;
    private ObjectMapper objectMapper;
    private static final String CONTENT_TYPE = "application/json";

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.service = new EmployeeServiceImpl();
        objectMapper = new ObjectMapper();
        super.init(config);
    }

    // Створення даних
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try (ServletInputStream in = req.getInputStream()) {
            EmployeeDtoRequest employeeDtoRequest =
                    this.objectMapper.readValue(in, EmployeeDtoRequest.class);
            Employee employee = this.service.create(employeeDtoRequest);
            String json;
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.setContentType(CONTENT_TYPE);
            if (employee != null) {
                json = this.objectMapper.writeValueAsString(
                        EmployeeDtoCreateResponse.of(true, employee)
                );
                resp.setContentLength(json.length());
                try (ServletOutputStream out = resp.getOutputStream()) {
                    out.println(json);
                    out.flush();
                }
            } else {
                json = this.objectMapper.writeValueAsString(
                        EmployeeDtoCreateResponse.of(false, null)
                );
                resp.setContentLength(json.length());
                try (ServletOutputStream out = resp.getOutputStream()) {
                    out.println(json);
                    out.flush();
                }
            }
        }
    }

    // Отримання даних
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String uri = req.getRequestURI();
        String[] uriParts = uri.split("/");
        String pathValue = uriParts[uriParts.length - 1];

        // Отримання даних за id
        if (pathValue != null && !pathValue.isEmpty() && !pathValue.equals("employees")) {
            Employee employee = this.service.getById(Long.parseLong(pathValue));
            String json;
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.setContentType(CONTENT_TYPE);
            if (employee != null) {
                json = this.objectMapper.writeValueAsString(
                        EmployeeDtoGetByIdResponse.of(Long.parseLong(pathValue),
                                true, employee)
                );
                resp.setContentLength(json.length());
                try (ServletOutputStream out = resp.getOutputStream()) {
                    out.println(json);
                    out.flush();
                }
            } else {
                json = this.objectMapper.writeValueAsString(
                        EmployeeDtoGetByIdResponse.of(Long.parseLong(pathValue),
                                false, null)
                );
                resp.setContentLength(json.length());
                try (ServletOutputStream out = resp.getOutputStream()) {
                    out.println(json);
                    out.flush();
                }
            }
        }

        // Отримання всіх даних
        if (pathValue != null && pathValue.equals("employees")) {
            List<Employee> list = this.service.getAll();
            String json;
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.setContentType(CONTENT_TYPE);
            if (list.isEmpty()) {
                json = this.objectMapper.writeValueAsString(
                        EmployeeDtoGetListResponse.of(true,
                                Collections.emptyList())
                );
                resp.setContentLength(json.length());
                try (ServletOutputStream out = resp.getOutputStream()) {
                    out.println(json);
                    out.flush();
                }
            } else {
                json = this.objectMapper.writeValueAsString(
                        EmployeeDtoGetListResponse.of(false,
                                list)
                );
                resp.setContentLength(json.length());
                try (ServletOutputStream out = resp.getOutputStream()) {
                    out.println(json);
                    out.flush();
                }
            }
        }
    }

    // Оновлення даних за id
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String uri = req.getRequestURI();
        String[] uriParts = uri.split("/");
        String pathValue = uriParts[uriParts.length - 1];
        if (pathValue != null && !pathValue.isEmpty()) {
            try (ServletInputStream in = req.getInputStream()) {
                Long employeeIdToUpdate = Long.parseLong(pathValue);
                EmployeeDtoRequest employeeDtoRequest =
                        this.objectMapper.readValue(in, EmployeeDtoRequest.class);
                Employee employeeToUpdate = service.getById(employeeIdToUpdate);
                if (employeeToUpdate != null) {
                    Employee employeeUpdated = this.service.update(employeeIdToUpdate,
                            employeeDtoRequest);
                    String json = this.objectMapper.writeValueAsString(
                            EmployeeDtoUpdateResponse.of(employeeIdToUpdate,
                                    true, employeeUpdated)
                    );
                    resp.setStatus(HttpServletResponse.SC_OK);
                    resp.setContentType(CONTENT_TYPE);
                    resp.setContentLength(json.length());
                    try (ServletOutputStream out = resp.getOutputStream()) {
                        out.println(json);
                        out.flush();
                    }
                } else {
                    String json = this.objectMapper.writeValueAsString(
                            EmployeeDtoUpdateResponse.of(employeeIdToUpdate,
                                    false, null)
                    );
                    resp.setStatus(HttpServletResponse.SC_OK);
                    resp.setContentType(CONTENT_TYPE);
                    resp.setContentLength(json.length());
                    try (ServletOutputStream out = resp.getOutputStream()) {
                        out.println(json);
                        out.flush();
                    }
                }
            }
        }
    }

    // Видалення даних за id
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String uri = req.getRequestURI();
        String[] uriParts = uri.split("/");
        String pathValue = uriParts[uriParts.length - 1];
        if (pathValue != null && !pathValue.isEmpty()) {
            Long employeeDeleteId = Long.parseLong(pathValue);
            boolean isEmployeeDeleted =
                    this.service.deleteById(employeeDeleteId);
            String json;
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.setContentType(CONTENT_TYPE);
            if (isEmployeeDeleted) {
                json = this.objectMapper.writeValueAsString(
                        EmployeeDtoDeleteResponse.of(employeeDeleteId,
                                true)
                );
                resp.setContentLength(json.length());
                try (ServletOutputStream out = resp.getOutputStream()) {
                    out.println(json);
                    out.flush();
                }
            } else {
                json = this.objectMapper.writeValueAsString(
                        EmployeeDtoDeleteResponse.of(employeeDeleteId,
                                false)
                );
                resp.setContentLength(json.length());
                try (ServletOutputStream out = resp.getOutputStream()) {
                    out.println(json);
                    out.flush();
                }
            }
        }
    }
}
