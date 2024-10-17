package com.example.employee_management_api.controller;

import com.example.employee_management_api.model.Employee;
import com.example.employee_management_api.model.TaxDetails;
import com.example.employee_management_api.service.EmployeeService;
import com.example.employee_management_api.service.TaxCalculationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    // 2. API endpoint to return employee's tax deduction for the current financial year
    @GetMapping("/{employeeId}/tax-deduction")
    public ResponseEntity<TaxDetails> getTaxDeduction(@PathVariable String employeeId) {
        Employee employee = employeeService.getEmployeeById(employeeId);
        if (employee == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        TaxDetails taxDetails = taxCalculationService.calculateTax(employee);
        return new ResponseEntity<>(taxDetails, HttpStatus.OK);
    }
}