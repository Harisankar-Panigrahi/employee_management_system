package com.ems.service;

import com.ems.dto.EmployeeRequest;
import com.ems.dto.EmployeeResponse;
import com.ems.entity.Employee;
import com.ems.exception.EmployeeNotFoundException;
import com.ems.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Test
    void shouldCreateEmployee() {

        EmployeeRequest request = EmployeeRequest.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john@example.com")
                .phone("9876543210")
                .designation("Developer")
                .salary(new BigDecimal("50000"))
                .build();

        Employee savedEmployee = Employee.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john@example.com")
                .phone("9876543210")
                .designation("Developer")
                .salary(new BigDecimal("50000"))
                .build();

        when(employeeRepository.save(any(Employee.class)))
                .thenReturn(savedEmployee);

        EmployeeResponse response =
                employeeService.createEmployee(request);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("John", response.getFirstName());
        assertEquals("Doe", response.getLastName());
        assertEquals("john@example.com", response.getEmail());

        verify(employeeRepository, times(1))
                .save(any(Employee.class));
    }

    @Test
    void shouldFindEmployeeById() {

        Employee employee = Employee.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john@example.com")
                .designation("Developer")
                .build();

        when(employeeRepository.findById(1L))
                .thenReturn(Optional.of(employee));

        EmployeeResponse response = employeeService.findEmployeeById(1L);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("John", response.getFirstName());
        assertEquals("Doe", response.getLastName());

        verify(employeeRepository).findById(1L);
    }
    @Test
    void shouldThrowExceptionWhenEmployeeNotFound() {

        when(employeeRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(EmployeeNotFoundException.class,
                () -> employeeService.findEmployeeById(1L));

        verify(employeeRepository).findById(1L);
    }

    @Test
    void shouldUpdateEmployee() {

        Employee existingEmployee = Employee.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john@example.com")
                .phone("9876543210")
                .designation("Developer")
                .salary(new BigDecimal("50000"))
                .build();

        EmployeeRequest request = EmployeeRequest.builder()
                .firstName("Jane")
                .lastName("Smith")
                .email("jane@example.com")
                .phone("9999999999")
                .designation("Senior Developer")
                .salary(new BigDecimal("70000"))
                .build();

        Employee updatedEmployee = Employee.builder()
                .id(1L)
                .firstName("Jane")
                .lastName("Smith")
                .email("jane@example.com")
                .phone("9999999999")
                .designation("Senior Developer")
                .salary(new BigDecimal("70000"))
                .build();

        when(employeeRepository.findById(1L))
                .thenReturn(Optional.of(existingEmployee));

        when(employeeRepository.save(any(Employee.class)))
                .thenReturn(updatedEmployee);

        EmployeeResponse response =
                employeeService.updateEmployee(1L, request);

        assertNotNull(response);
        assertEquals("Jane", response.getFirstName());
        assertEquals("Smith", response.getLastName());
        assertEquals("jane@example.com", response.getEmail());

        verify(employeeRepository).findById(1L);
        verify(employeeRepository).save(any(Employee.class));
    }
    @Test
    void shouldDeleteEmployee() {

        Employee employee = Employee.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .build();

        when(employeeRepository.findById(1L))
                .thenReturn(Optional.of(employee));

        ResponseEntity<String> response =
                employeeService.deleteEmployeeById(1L);

        assertEquals(200, response.getStatusCode().value());
        assertEquals("Employee deleted successfully", response.getBody());

        verify(employeeRepository).findById(1L);
        verify(employeeRepository).delete(employee);
    }

}