package com.ems.Mapper;

import com.ems.dto.EmployeeResponse;
import com.ems.entity.Employee;

public class EmployeeMapper {
    public static EmployeeResponse toResponse(Employee employee) {
        return EmployeeResponse.builder()
                .id(employee.getId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .email(employee.getEmail())
                .designation(employee.getDesignation())
                .build();
    }
}
