package com.example.employee_management_api.model;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Employee {

    @Id
    private String employeeId;

    @NotBlank(message = "First name is mandatory")
    private String firstName;

    @NotBlank(message = "Last name is mandatory")
    private String lastName;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is mandatory")
    private String email;

    @ElementCollection
    private List<@Pattern(regexp = "\\d{10}", message = "Phone number must be 10 digits") String> phoneNumbers;

    @NotBlank(message = "Date of joining is mandatory")
    private String doj;

    @Positive(message = "Salary must be positive")
    private double salary;

}
