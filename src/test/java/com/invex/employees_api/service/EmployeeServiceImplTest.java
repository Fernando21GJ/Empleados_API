package com.invex.employees_api.service;

import com.invex.employees_api.dto.EmployeeDTO;
import com.invex.employees_api.exception.ResourceNotFoundException;
import com.invex.employees_api.model.Employee;
import com.invex.employees_api.repository.EmployeeRepository;
import com.invex.employees_api.service.impl.EmployeeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee sampleEmployee;
    private EmployeeDTO sampleDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        sampleEmployee = new Employee(1L, "John", "A.", "Doe", "Smith",
                30, "Male", LocalDate.of(1994, 5, 20),
                "Developer", LocalDateTime.now(), true);

        sampleDTO = new EmployeeDTO();
        sampleDTO.setFirstName("John");
        sampleDTO.setMiddleName("A.");
        sampleDTO.setLastName("Doe");
        sampleDTO.setSecondLastName("Smith");
        sampleDTO.setAge(30);
        sampleDTO.setGender("Male");
        sampleDTO.setBirthDate(LocalDate.of(1994, 5, 20));
        sampleDTO.setPosition("Developer");
        sampleDTO.setActive(true);
    }

    @Test
    void testGetAllEmployees() {
        when(employeeRepository.findAll()).thenReturn(List.of(sampleEmployee));

        List<Employee> result = employeeService.getAllEmployees();

        assertEquals(1, result.size());
        assertEquals("John", result.get(0).getFirstName());
        verify(employeeRepository).findAll();
    }

    @Test
    void testGetEmployeeByIdFound() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(sampleEmployee));

        Employee result = employeeService.getEmployeeById(1L);

        assertEquals("John", result.getFirstName());
    }

    @Test
    void testGetEmployeeByIdNotFound() {
        when(employeeRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> employeeService.getEmployeeById(2L));
    }

    @Test
    void testCreateEmployee() {
        when(employeeRepository.save(any(Employee.class))).thenReturn(sampleEmployee);

        Employee result = employeeService.createEmployee(sampleDTO);

        assertEquals("John", result.getFirstName());
        verify(employeeRepository).save(any(Employee.class));
    }

    @Test
    void testUpdateEmployee() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(sampleEmployee));
        when(employeeRepository.save(any(Employee.class))).thenReturn(sampleEmployee);

        Employee updated = employeeService.updateEmployee(1L, sampleDTO);

        assertEquals("John", updated.getFirstName());
    }

    @Test
    void testDeleteEmployee() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(sampleEmployee));
        doNothing().when(employeeRepository).delete(sampleEmployee);

        assertDoesNotThrow(() -> employeeService.deleteEmployee(1L));
        verify(employeeRepository).delete(sampleEmployee);
    }

    @Test
    void testSearchEmployeesByName() {
        when(employeeRepository.findByFirstNameContainingIgnoreCase("Jo"))
                .thenReturn(List.of(sampleEmployee));

        List<Employee> results = employeeService.searchEmployeesByName("Jo");

        assertEquals(1, results.size());
    }
}
