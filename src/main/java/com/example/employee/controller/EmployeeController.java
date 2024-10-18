package com.example.employee.controller;

import com.example.employee.model.Employee;
import com.example.employee.model.TaxDetails;
import com.example.employee.service.EmployeeService;
import com.example.employee.service.TaxCalculationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private TaxCalculationService taxCalculationService;

    // 1. API endpoint to store employee details (with validation)
    @PostMapping
    public ResponseEntity<Employee> createEmployee(@Valid @RequestBody Employee employee) {
        Employee savedEmployee = employeeService.saveEmployee(employee);
        return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
    }

    @GetMapping("/{employeeId}/tax-deduction")
    public ResponseEntity<?> getTaxDeduction(@PathVariable int employeeId) {
        return Optional.ofNullable(employeeService.getEmployeeById(employeeId))
                .<ResponseEntity<?>>map(employee -> {
                    TaxDetails taxDetails = taxCalculationService.calculateTax(employee);
                    return ResponseEntity.ok(taxDetails); // Return OK with tax details
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Employee not found with ID: " + employeeId)); // Return NOT_FOUND with a message
    }



}
