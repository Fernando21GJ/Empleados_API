package com.invex.employees_api.service;


import com.invex.employees_api.dto.EmployeeDTO;
import com.invex.employees_api.model.Employee;

import java.util.List;

public interface EmployeeService {
    List<Employee> getAllEmployees();
    Employee getEmployeeById(Long id);
    List<Employee> searchEmployeesByName(String name);
    Employee createEmployee(EmployeeDTO employeeDTO);
    List<Employee> createEmployees(List<EmployeeDTO> employees);
    Employee updateEmployee(Long id, EmployeeDTO employeeDTO);
    void deleteEmployee(Long id);
}
