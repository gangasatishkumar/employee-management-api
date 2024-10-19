package com.example.employee.service;

import com.example.employee.model.Employee;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class EmployeeService {

    private final Map<String, Employee> employeeRepository = new HashMap<>();

    // Save employee method using lambda
    public Employee saveEmployee(Employee employee) {
        return employeeRepository.compute(String.valueOf(employee.getEmployeeId()),
                (key, existingEmployee) -> {
                    if (existingEmployee == null) {
                        return employee;  // If no existing employee with the same ID, save the new one
                    } else {
                        // Optional: handle the case where the employee already exists, for now, we replace it.
                        // You can also throw an exception or update some fields here.
                        return employee;
                    }
                });
    }

    // Get employee by ID method with lambda (using Optional to handle null value)
    public Employee getEmployeeById(int employeeId) {
        return employeeRepository.getOrDefault(String.valueOf(employeeId), null);  // Returns null if the employeeId doesn't exist
    }
}