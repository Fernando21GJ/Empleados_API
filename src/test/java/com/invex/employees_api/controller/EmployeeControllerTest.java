package com.invex.employees_api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.invex.employees_api.dto.EmployeeDTO;
import com.invex.employees_api.model.Employee;
import com.invex.employees_api.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.security.test.context.support.WithMockUser;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;


@WithMockUser(username = "testuser", roles = {"USER"})
@WebMvcTest(controllers = EmployeeController.class)
@ActiveProfiles("test")
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    private Employee employee;
    private EmployeeDTO employeeDTO;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        employee = new Employee(1L, "John", "A.", "Doe", "Smith",
                30, "Male", LocalDate.of(1994, 5, 20),
                "Developer", LocalDateTime.now(), true);

        employeeDTO = new EmployeeDTO();
        employeeDTO.setFirstName("John");
        employeeDTO.setMiddleName("A.");
        employeeDTO.setLastName("Doe");
        employeeDTO.setSecondLastName("Smith");
        employeeDTO.setAge(30);
        employeeDTO.setGender("Male");
        employeeDTO.setBirthDate(LocalDate.of(1994, 5, 20));
        employeeDTO.setPosition("Developer");
        employeeDTO.setActive(true);

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Test
    void shouldGetAllEmployees() throws Exception {
        when(employeeService.getAllEmployees()).thenReturn(List.of(employee));

        mockMvc.perform(get("/api/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("John"));
    }

    @Test
    void shouldGetEmployeeById() throws Exception {
        when(employeeService.getEmployeeById(1L)).thenReturn(employee);

        mockMvc.perform(get("/api/employees/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"));
    }

    @WithMockUser(username = "testuser", roles = {"USER"})
    @Test
    void shouldCreateEmployee() throws Exception {
        when(employeeService.createEmployee(any(EmployeeDTO.class))).thenReturn(employee);

        mockMvc.perform(post("/api/employees")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(List.of(employeeDTO))))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value("John"));
    }

    @WithMockUser(username = "testuser", roles = {"USER"})
    @Test
    void shouldUpdateEmployee() throws Exception {
        when(employeeService.updateEmployee(eq(1L), any(EmployeeDTO.class))).thenReturn(employee);

        mockMvc.perform(put("/api/employees/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employeeDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"));
    }

    @WithMockUser(username = "testuser", roles = {"USER"})
    @Test
    void shouldDeleteEmployee() throws Exception {
        doNothing().when(employeeService).deleteEmployee(1L);

        mockMvc.perform(delete("/api/employees/1")
                        .with(csrf()))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldSearchByName() throws Exception {
        when(employeeService.searchEmployeesByName("Jo")).thenReturn(Collections.singletonList(employee));

        mockMvc.perform(get("/api/employees/search?name=Jo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("John"));
    }
}
