package com.ems.service;

import com.ems.controller.EmployeeController;
import com.ems.dto.EmployeeRequest;
import com.ems.dto.EmployeeResponse;
import com.ems.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// Import your SecurityConfig if needed
// @Import(SecorityConfig.class)

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldGetEmployeeById() throws Exception {

        EmployeeResponse response = EmployeeResponse.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john@example.com")
                .designation("Developer")
                .build();

        when(employeeService.findEmployeeById(1L))
                .thenReturn(response);

        mockMvc.perform(get("/api/employees/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.email").value("john@example.com"));
    }

    @Test
    void shouldCreateEmployee() throws Exception {

        EmployeeRequest request = EmployeeRequest.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john@example.com")
                .phone("9876543210")
                .designation("Developer")
                .salary(new BigDecimal("50000"))
                .build();

        EmployeeResponse response = EmployeeResponse.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john@example.com")
                .designation("Developer")
                .build();

        when(employeeService.createEmployee(any(EmployeeRequest.class)))
                .thenReturn(response);

        mockMvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.email").value("john@example.com"));
    }
    @Test
    void shouldUpdateEmployee() throws Exception {

        EmployeeRequest request = EmployeeRequest.builder()
                .firstName("Jane")
                .lastName("Smith")
                .email("jane@example.com")
                .phone("9999999999")
                .designation("Senior Developer")
                .salary(new BigDecimal("70000"))
                .build();

        EmployeeResponse response = EmployeeResponse.builder()
                .id(1L)
                .firstName("Jane")
                .lastName("Smith")
                .email("jane@example.com")
                .designation("Senior Developer")
                .build();

        when(employeeService.updateEmployee(eq(1L), any(EmployeeRequest.class)))
                .thenReturn(response);

        mockMvc.perform(put("/api/employees/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Jane"))
                .andExpect(jsonPath("$.lastName").value("Smith"));
    }
    @Test
    void shouldDeleteEmployee() throws Exception {

        when(employeeService.deleteEmployeeById(1L))
                .thenReturn(ResponseEntity.ok("Employee deleted successfully"));

        mockMvc.perform(delete("/api/employees/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Employee deleted successfully"));
    }
    @Test
    void shouldSearchEmployees() throws Exception {

        EmployeeResponse response = EmployeeResponse.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john@example.com")
                .designation("Developer")
                .build();

        when(employeeService.searchEmployees("John"))
                .thenReturn(List.of(response));

        mockMvc.perform(get("/api/employees/search")
                        .param("name", "John"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("John"));
    }
    @Test
    void shouldGetAllEmployees() throws Exception {

        EmployeeResponse employee = EmployeeResponse.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john@example.com")
                .designation("Developer")
                .build();

        Page<EmployeeResponse> page =
                new PageImpl<>(List.of(employee));

        when(employeeService.getAllEmployees(0,5,"id"))
                .thenReturn(page);

        mockMvc.perform(get("/api/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].firstName")
                        .value("John"));
    }

}