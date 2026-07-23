package com.ems.controller;

import com.ems.dto.EmployeeRequest;
import com.ems.dto.EmployeeResponse;
import com.ems.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;
    @PostMapping
    public EmployeeResponse createEmployee(@Valid @RequestBody EmployeeRequest request) {
        return employeeService.createEmployee(request);
}

    @GetMapping
    public Page<EmployeeResponse> getAllEmployees(

            @RequestParam(defaultValue = "0") int page,

            @RequestParam(defaultValue = "5") int size,

            @RequestParam(defaultValue = "id") String sortBy) {

        return employeeService.getAllEmployees(page, size, sortBy);
    }
    @GetMapping("/{id}")
    public EmployeeResponse findEmployeeById(@PathVariable Long id) {
        return employeeService.findEmployeeById(id);
    }

    @GetMapping("/search")
    public List<EmployeeResponse> searchEmployees(
            @RequestParam String name) {

        return employeeService.searchEmployees(name);
    }

    @PutMapping("/{id}")
    public EmployeeResponse updateEmployee(@PathVariable Long id,@Valid @RequestBody EmployeeRequest request) {
        return employeeService.updateEmployee(id,request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id){
        return employeeService.deleteEmployeeById(id);
    }

    @PostMapping(
            value = "/{id}/upload-photo",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public EmployeeResponse uploadPhoto(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file)
            throws IOException {

        return employeeService.uploadPhoto(id, file);
    }
}
