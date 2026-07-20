package com.ems.service;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import com.ems.dto.EmployeeRequest;
import com.ems.dto.EmployeeResponse;
import com.ems.entity.Employee;
import com.ems.exception.EmployeeNotFoundException;
import com.ems.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

import static java.util.Arrays.stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private Employee getEmployeeByIdOrThrow(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + id));
    }
    private EmployeeResponse mapToResponse(Employee employee) {
        return EmployeeResponse.builder()
                .id(employee.getId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .email(employee.getEmail())
                .designation(employee.getDesignation())
                .photoUrl(employee.getPhotoUrl())
                .build();
    }
    @Override
    public EmployeeResponse createEmployee(EmployeeRequest request) {
        Employee employee = Employee.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .designation(request.getDesignation())
                .salary(request.getSalary())
                .build();

        log.info("Creating employee with email: {}", request.getEmail());

        Employee savedEmployee = employeeRepository.save(employee);

        log.info("Employee created successfully with ID: {}", savedEmployee.getId());
        return mapToResponse(savedEmployee);


    }

    @Override
    public Page<EmployeeResponse> getAllEmployees(int page, int size, String sortBy) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));

        Page<Employee> employees = employeeRepository.findAll(pageable);

        return employees.map(employee -> EmployeeResponse.builder()
                .id(employee.getId())
                .photoUrl(employee.getPhotoUrl())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .email(employee.getEmail())
                .designation(employee.getDesignation())
                .build());
    }

    @Override
    public EmployeeResponse findEmployeeById(Long id) {
        Employee employee = getEmployeeByIdOrThrow(id);
        return mapToResponse(employee);
    }

    @Override
    public List<EmployeeResponse> searchEmployees(String name) {

        return employeeRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(name,name)
                .stream()
                .map(employee -> EmployeeResponse.builder()
                        .id(employee.getId())
                        .photoUrl(employee.getPhotoUrl())
                        .firstName(employee.getFirstName())
                        .lastName(employee.getLastName())
                        .email(employee.getEmail())
                        .designation(employee.getDesignation())
                        .build())
                .toList();
    }

    @Override
    public EmployeeResponse updateEmployee(Long id, EmployeeRequest request) {
        Employee employee = getEmployeeByIdOrThrow(id);
        employee.setFirstName(request.getFirstName());
        employee.setLastName(request.getLastName());
        employee.setEmail(request.getEmail());
        employee.setPhone(request.getPhone());
        employee.setDesignation(request.getDesignation());
        employee.setSalary(request.getSalary());

        log.info("Updating employee with ID: {}", id);
        Employee updateEmployee = employeeRepository.save(employee);
        log.info("Employee {} updated successfully", id);

        return mapToResponse(updateEmployee);
    }

    @Override
    public ResponseEntity<String> deleteEmployeeById(Long id) {
        Employee employee = getEmployeeByIdOrThrow(id);

        log.info("Deleting employee with ID: {}", id);
        employeeRepository.delete(employee);
        log.info("Employee {} deleted successfully", id);
        return ResponseEntity.ok("Employee deleted successfully");
    }

    @Override
    public EmployeeResponse uploadPhoto(Long id, MultipartFile file) throws IOException {

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

        Path uploadPath = Paths.get("uploads");

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        Files.copy(
                file.getInputStream(),
                uploadPath.resolve(fileName),
                StandardCopyOption.REPLACE_EXISTING
        );

        employee.setPhotoUrl(fileName);

        Employee savedEmployee = employeeRepository.save(employee);

        return EmployeeResponse.builder()
                .id(savedEmployee.getId())
                .firstName(savedEmployee.getFirstName())
                .lastName(savedEmployee.getLastName())
                .email(savedEmployee.getEmail())
                .designation(savedEmployee.getDesignation())
                .photoUrl(savedEmployee.getPhotoUrl())
                .build();
    }

    @Override
    public Resource getEmployeePhoto(Long id) throws IOException {

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        Path path = Paths.get("uploads")
                .resolve(employee.getPhotoUrl());

        Resource resource = new UrlResource(path.toUri());

        if (!resource.exists()) {
            throw new RuntimeException("Photo not found");
        }

        return resource;
    }
}
