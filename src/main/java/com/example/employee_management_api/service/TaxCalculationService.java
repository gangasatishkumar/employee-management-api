package com.example.employee_management_api.service;

import com.example.employee_management_api.model.Employee;
import com.example.employee_management_api.model.TaxDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
public class TaxCalculationService {

    public TaxDetails calculateTax(Employee employee) {
        LocalDate today = LocalDate.now();
        LocalDate doj = LocalDate.parse(employee.getDoj());
        int totalMonths = calculateMonthsWorked(doj, today);

        double yearlySalary = employee.getSalary() * totalMonths;

        double tax = calculateTaxFromSlabs(yearlySalary);
        double cess = 0;
        if (yearlySalary > 2500000) {
            cess = (yearlySalary - 2500000) * 0.02;
        }

        return new TaxDetails(employee.getEmployeeId(), employee.getFirstName(), employee.getLastName(), yearlySalary, tax, cess);
    }

    private int calculateMonthsWorked(LocalDate doj, LocalDate today) {
        LocalDate financialYearStart = LocalDate.of(today.getYear(), 4, 1);
        if (doj.isAfter(financialYearStart)) {
            financialYearStart = doj;
        }
        return (int) ChronoUnit.MONTHS.between(financialYearStart, today);
    }

    private double calculateTaxFromSlabs(double yearlySalary) {
        double tax = 0;
        if (yearlySalary > 1000000) {
            tax += (yearlySalary - 1000000) * 0.20;
            yearlySalary = 1000000;
        }
        if (yearlySalary > 500000) {
            tax += (yearlySalary - 500000) * 0.10;
            yearlySalary = 500000;
        }
        if (yearlySalary > 250000) {
            tax += (yearlySalary - 250000) * 0.05;
        }
        return tax;
    }
}
