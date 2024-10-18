package com.example.employee.controller.service;

import com.example.employee.model.Employee;
import com.example.employee.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeServiceTest {

    private EmployeeService employeeService;

    @BeforeEach
    void setUp() {
        // Initialize the service before each test
        employeeService = new EmployeeService();
    }

    @Test
    void testSaveEmployee() {
        // Create a new employee object
        Employee employee = new Employee();
        employee.setEmployeeId(1);
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setEmail("john.doe@example.com");
        employee.setSalary(5000.0);

        // Save employee
        Employee savedEmployee = employeeService.saveEmployee(employee);

        // Assertions to verify the employee was saved correctly
        assertNotNull(savedEmployee);
        assertEquals(1, savedEmployee.getEmployeeId());
        assertEquals("John", savedEmployee.getFirstName());
        assertEquals("Doe", savedEmployee.getLastName());
        assertEquals("john.doe@example.com", savedEmployee.getEmail());
        assertEquals(5000.0, savedEmployee.getSalary());
    }

    @Test
    void testGetEmployeeById_ExistingId() {
        // Create and save an employee
        Employee employee = new Employee();
        employee.setEmployeeId(1);
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setEmail("john.doe@example.com");
        employee.setSalary(5000.0);

        employeeService.saveEmployee(employee);

        // Fetch employee by ID
        Employee fetchedEmployee = employeeService.getEmployeeById(1);

        // Verify the employee is fetched correctly
        assertNotNull(fetchedEmployee);
        assertEquals("John", fetchedEmployee.getFirstName());
        assertEquals("Doe", fetchedEmployee.getLastName());
    }

    @Test
    void testGetEmployeeById_NonExistingId() {
        // Attempt to fetch employee with non-existing ID
        Employee employee = employeeService.getEmployeeById(100);

        // Verify that null is returned
        assertNull(employee);
    }
}
