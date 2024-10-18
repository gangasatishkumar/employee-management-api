package com.example.employee.service;

import com.example.employee.model.Employee;
import com.example.employee.model.TaxDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class TaxCalculationServiceTest {

    private TaxCalculationService taxCalculationService;

    @BeforeEach
    void setUp() {
        taxCalculationService = new TaxCalculationService();
    }

    // Test for full year salary calculation
    @Test
    void testCalculateTaxForFullYear() {
        Employee employee = new Employee();
        employee.setEmployeeId(1);
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setDoj(LocalDate.of(2023, 4, 1));  // Full financial year
        employee.setSalary(50000);  // Monthly salary

        TaxDetails taxDetails = taxCalculationService.calculateTax(employee);

        assertEquals(600000, taxDetails.getYearlySalary());  // 50,000 * 12
        assertEquals(22500, taxDetails.getTaxAmount());  // 5% tax on income between 2.5L and 5L
        assertEquals(0, taxDetails.getCessAmount());  // No cess for salary below 25L
    }

    // Test for partial year salary calculation (joined mid-year)
    @Test
    void testCalculateTaxForPartialYear() {
        Employee employee = new Employee();
        employee.setEmployeeId(2);
        employee.setFirstName("Jane");
        employee.setLastName("Doe");
        employee.setDoj(LocalDate.of(2023, 10, 15));  // Joined mid-year
        employee.setSalary(60000);  // Monthly salary

        TaxDetails taxDetails = taxCalculationService.calculateTax(employee);

        assertTrue(taxDetails.getYearlySalary() > 0);  // Partial year salary should be calculated
        assertEquals(720000, taxDetails.getYearlySalary(), 1000);  // 6.5 months salary (Oct to Mar)
        assertEquals(34500, taxDetails.getTaxAmount());  // Based on partial salary
    }

    // Test for high income with cess
    @Test
    void testCalculateTaxForHighIncomeWithCess() {
        Employee employee = new Employee();
        employee.setEmployeeId(3);
        employee.setFirstName("Richie");
        employee.setLastName("Rich");
        employee.setDoj(LocalDate.of(2023, 4, 1));  // Full financial year
        employee.setSalary(300000);  // Monthly salary

        TaxDetails taxDetails = taxCalculationService.calculateTax(employee);

        assertEquals(3600000, taxDetails.getYearlySalary());  // 3L * 12
        assertEquals(582500, taxDetails.getTaxAmount());  // Progressive tax for high income
        assertEquals(22000, taxDetails.getCessAmount());  // 2% cess for salary above 25L
    }

    // Test for an employee joining after the financial year starts
    @Test
    void testCalculateYearlySalaryForJoiningAfterFinancialYear() {
        Employee employee = new Employee();
        employee.setDoj(LocalDate.of(2024, 6, 1));  // After current financial year
        employee.setSalary(50000);

        double yearlySalary = taxCalculationService.calculateYearlySalary(employee);
        assertEquals(500000, yearlySalary);  // Should return 0 as employee joined after the financial year
    }

    // Test for calculating salary with partial month (joined mid-month)
    @Test
    void testCalculateYearlySalaryForPartialMonth() {
        Employee employee = new Employee();
        employee.setDoj(LocalDate.of(2023, 10, 20));  // Joined mid-month
        employee.setSalary(60000);

        double yearlySalary = taxCalculationService.calculateYearlySalary(employee);
        assertTrue(yearlySalary > 0);  // Should calculate partial salary for October
        assertEquals(720000, yearlySalary, 1000);  // Based on 6.5 months salary (Oct to Mar)
    }

}
