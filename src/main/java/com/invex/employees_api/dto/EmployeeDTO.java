package com.invex.employees_api.dto;

import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
public class EmployeeDTO {

    @NotBlank
    private String firstName;

    private String middleName;

    @NotBlank
    private String lastName;

    private String secondLastName;

    @Min(18)
    @Max(65)
    private int age;

    @NotBlank
    private String gender;

    @NotNull
    private LocalDate birthDate;

    @NotBlank
    private String position;

    private Boolean active;
}
