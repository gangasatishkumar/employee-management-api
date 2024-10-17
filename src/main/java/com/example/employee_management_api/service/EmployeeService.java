package com.example.employee_management_api.service;

import com.example.employee_management_api.model.Employee;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class EmployeeService {

    private Map<String, Employee> employeeRepository = new HashMap<>();

    // Save employee method
    public Employee saveEmployee(Employee employee) {
        employeeRepository.put(employee.getEmployeeId(), employee);
        return employee;
    }

    // Get employee by ID method
    public Employee getEmployeeById(String employeeId) {
        return employeeRepository.get(employeeId);  // Returns null if the employeeId doesn't exist
    }
}
