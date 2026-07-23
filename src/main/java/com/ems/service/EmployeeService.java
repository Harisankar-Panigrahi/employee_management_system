package com.ems.service;

import com.ems.dto.EmployeeRequest;
import com.ems.dto.EmployeeResponse;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface EmployeeService {
    EmployeeResponse createEmployee(EmployeeRequest request);

    Page<EmployeeResponse> getAllEmployees(int page, int size, String sortBy);
    EmployeeResponse findEmployeeById(Long id);
    EmployeeResponse updateEmployee(Long id, EmployeeRequest request);
    ResponseEntity<String> deleteEmployeeById(Long id);
    List<EmployeeResponse> searchEmployees(String name);
    EmployeeResponse uploadPhoto(Long id, MultipartFile file) throws IOException;
    Resource getEmployeePhoto(Long id) throws IOException;

}
