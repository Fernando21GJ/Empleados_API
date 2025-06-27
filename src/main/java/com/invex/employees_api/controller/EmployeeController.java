package com.invex.employees_api.controller;

import com.invex.employees_api.dto.EmployeeDTO;
import com.invex.employees_api.model.Employee;
import com.invex.employees_api.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
@Validated
public class EmployeeController {

    private final EmployeeService employeeService;

    // GET /employees
    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    // GET /employees/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

    // GET /employees/search?name=partialName
    @GetMapping("/search")
    public ResponseEntity<List<Employee>> searchEmployeesByName(@RequestParam String name) {
        return ResponseEntity.ok(employeeService.searchEmployeesByName(name));
    }

    // POST /employees (single or multiple)
    @PostMapping
    public ResponseEntity<?> createEmployees(@RequestBody @Valid List<EmployeeDTO> dtos) {
        if (dtos.size() == 1) {
            Employee created = employeeService.createEmployee(dtos.get(0));
            return new ResponseEntity<>(created, HttpStatus.CREATED);
        } else {
            List<Employee> createdList = employeeService.createEmployees(dtos);
            return new ResponseEntity<>(createdList, HttpStatus.CREATED);
        }
    }

    // PUT /employees/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(
            @PathVariable Long id,
            @RequestBody @Valid EmployeeDTO dto) {
        return ResponseEntity.ok(employeeService.updateEmployee(id, dto));
    }

    // DELETE /employees/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }
}
