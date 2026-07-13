package com.ems.service;

import com.ems.dto.EmployeeRequest;
import com.ems.dto.EmployeeResponse;

import java.util.List;

public interface EmployeeService {
    EmployeeResponse createEmployee(EmployeeRequest request);

    List<EmployeeResponse> getAllEmployees();
    EmployeeResponse findEmployeeById(Long id);
    EmployeeResponse updateEmployee(Long id, EmployeeRequest request);
    String deleteEmployeeById(Long id);
}
