package com.ems.service;

import com.ems.dto.EmployeeRequest;
import com.ems.dto.EmployeeResponse;
import com.ems.entity.Employee;
import com.ems.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Arrays.stream;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private Employee getEmployeeByIdOrThrow(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));
    }
    private EmployeeResponse mapToResponse(Employee employee) {
        return EmployeeResponse.builder()
                .id(employee.getId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .email(employee.getEmail())
                .designation(employee.getDesignation())
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

        Employee savedEmployee = employeeRepository.save(employee);
        return mapToResponse(savedEmployee);


    }

    @Override
    public List<EmployeeResponse> getAllEmployees() {
        return employeeRepository.findAll()
                .stream()
                .map(employee -> EmployeeResponse.builder()
                        .id(employee.getId())
                        .firstName(employee.getFirstName())
                        .lastName(employee.getLastName())
                        .email(employee.getEmail())
                        .designation(employee.getDesignation())
                        .build())
                .toList();
    }

    @Override
    public EmployeeResponse findEmployeeById(Long id) {
        Employee employee = getEmployeeByIdOrThrow(id);
        return mapToResponse(employee);
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

        Employee updateEmployee = employeeRepository.save(employee);

        return mapToResponse(updateEmployee);
    }

    @Override
    public String deleteEmployeeById(Long id) {
        Employee employee = getEmployeeByIdOrThrow(id);
       employeeRepository.delete(employee);
        return "Employee Deleted sucessfully";
    }

}
