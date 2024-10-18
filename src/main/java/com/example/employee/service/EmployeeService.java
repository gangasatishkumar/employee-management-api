package com.example.employee.service;

import com.example.employee.model.Employee;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class EmployeeService {

    private Map<String, Employee> employeeRepository = new HashMap<>();

    // Save employee method
    public Employee saveEmployee(Employee employee) {
        employeeRepository.put(String.valueOf(employee.getEmployeeId()), employee);
        return employee;
    }

    // Get employee by ID method
    public Employee getEmployeeById(int employeeId) {
        return employeeRepository.get(String.valueOf(employeeId));  // Returns null if the employeeId doesn't exist
    }
}
