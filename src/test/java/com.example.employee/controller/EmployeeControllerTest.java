package com.example.employee.controller;

import com.example.employee.model.Employee;
import com.example.employee.model.TaxDetails;
import com.example.employee.service.EmployeeService;
import com.example.employee.service.TaxCalculationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EmployeeControllerTest {

    @Mock
    private EmployeeService employeeService;

    @Mock
    private TaxCalculationService taxCalculationService;

    @InjectMocks
    private EmployeeController employeeController;

    private Employee employee;
    private TaxDetails taxDetails;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        employee = new Employee();
        employee.setEmployeeId(102);
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setEmail("john.doe@example.com");
        employee.setPhoneNumbers(List.of("1234567890"));
        employee.setDoj(LocalDate.now());
        employee.setSalary(50000);

        taxDetails = new TaxDetails(); // No-args constructor needed
        taxDetails.setTaxAmount(5000);
        taxDetails.setCessAmount(200);
    }

    @Test
    public void testCreateEmployee_Success() {
        when(employeeService.saveEmployee(any(Employee.class))).thenReturn(employee);

        ResponseEntity<Employee> response = employeeController.createEmployee(employee);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(employee, response.getBody());
        verify(employeeService, times(1)).saveEmployee(employee);
    }

    @Test
    public void testGetTaxDeduction_Success() {
        when(employeeService.getEmployeeById(102)).thenReturn(employee);
        when(taxCalculationService.calculateTax(employee)).thenReturn(taxDetails);

        ResponseEntity<?> response = employeeController.getTaxDeduction(102);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(taxDetails, response.getBody());
        verify(employeeService, times(1)).getEmployeeById(102);
        verify(taxCalculationService, times(1)).calculateTax(employee);
    }

    @Test
    public void testGetTaxDeduction_EmployeeNotFound() {
        when(employeeService.getEmployeeById(102)).thenReturn(null);

        ResponseEntity<?> response = employeeController.getTaxDeduction(102);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Employee not found with ID: 102", response.getBody());
        verify(employeeService, times(1)).getEmployeeById(102);
        verify(taxCalculationService, times(0)).calculateTax(any(Employee.class));
    }
}
