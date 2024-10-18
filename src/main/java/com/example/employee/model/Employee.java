package com.example.employee.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private int employeeId;

    private String firstName;
    private String lastName;
    private String email;

    @ElementCollection  // Used to map a collection of basic types
    private List<String> phoneNumbers;

    private LocalDate doj;  // Date of Joining
    private double salary;  // Monthly salary
}
