package com.ems.service;

import com.ems.dto.EmployeeRequest;
import com.ems.dto.EmployeeResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface EmployeeService {
    EmployeeResponse createEmployee(EmployeeRequest request);

    List<EmployeeResponse> getAllEmployees();
    EmployeeResponse findEmployeeById(Long id);
    EmployeeResponse updateEmployee(Long id, EmployeeRequest request);
    ResponseEntity<String> deleteEmployeeById(Long id);
}
