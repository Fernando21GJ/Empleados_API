package com.invex.employees_api.service.impl;

import com.invex.employees_api.dto.EmployeeDTO;
import com.invex.employees_api.exception.ResourceNotFoundException;
import com.invex.employees_api.model.Employee;
import com.invex.employees_api.repository.EmployeeRepository;
import com.invex.employees_api.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + id));
    }

    @Override
    public List<Employee> searchEmployeesByName(String name) {
        return employeeRepository.findByFirstNameContainingIgnoreCase(name);
    }

    @Override
    public Employee createEmployee(EmployeeDTO dto) {
        Employee employee = mapToEntity(dto);
        employee.setCreatedAt(LocalDateTime.now());
        return employeeRepository.save(employee);
    }

    @Override
    public List<Employee> createEmployees(List<EmployeeDTO> dtos) {
        List<Employee> employees = dtos.stream()
                .map(dto -> {
                    Employee e = mapToEntity(dto);
                    e.setCreatedAt(LocalDateTime.now());
                    return e;
                }).collect(Collectors.toList());
        return employeeRepository.saveAll(employees);
    }

    @Override
    public Employee updateEmployee(Long id, EmployeeDTO dto) {
        Employee employee = getEmployeeById(id); // valida existencia
        updateEntityFromDTO(employee, dto);
        return employeeRepository.save(employee);
    }

    @Override
    public void deleteEmployee(Long id) {
        Employee existing = getEmployeeById(id); // valida existencia
        employeeRepository.delete(existing);
    }

    private Employee mapToEntity(EmployeeDTO dto) {
        return Employee.builder()
                .firstName(dto.getFirstName())
                .middleName(dto.getMiddleName())
                .lastName(dto.getLastName())
                .secondLastName(dto.getSecondLastName())
                .age(dto.getAge())
                .gender(dto.getGender())
                .birthDate(dto.getBirthDate())
                .position(dto.getPosition())
                .active(dto.getActive() != null ? dto.getActive() : true)
                .build();
    }

    private void updateEntityFromDTO(Employee emp, EmployeeDTO dto) {
        emp.setFirstName(dto.getFirstName());
        emp.setMiddleName(dto.getMiddleName());
        emp.setLastName(dto.getLastName());
        emp.setSecondLastName(dto.getSecondLastName());
        emp.setAge(dto.getAge());
        emp.setGender(dto.getGender());
        emp.setBirthDate(dto.getBirthDate());
        emp.setPosition(dto.getPosition());
        if (dto.getActive() != null) emp.setActive(dto.getActive());
    }
}
