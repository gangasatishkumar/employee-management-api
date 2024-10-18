package com.example.employee.service;

import com.example.employee.model.Employee;
import com.example.employee.model.TaxDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
public class TaxCalculationService {

    public TaxDetails calculateTax(Employee employee) {
        double yearlySalary = calculateYearlySalary(employee);
        double taxAmount = calculateTaxAmount(yearlySalary);
        double cessAmount = calculateCessAmount(yearlySalary);

        return new TaxDetails(
                String.valueOf(employee.getEmployeeId()),
                employee.getFirstName(),
                employee.getLastName(),
                yearlySalary,
                taxAmount,
                cessAmount
        );
    }

    public double calculateYearlySalary(Employee employee) {
        LocalDate doj = employee.getDoj();
        LocalDate currentYearStart = LocalDate.of(LocalDate.now().getYear(), 4, 1);
        LocalDate currentYearEnd = LocalDate.of(LocalDate.now().plusYears(1).getYear(), 3, 31);

        if (doj.isAfter(currentYearEnd)) {
            return 0;  // Employee joined after the financial year
        }

        LocalDate effectiveDoj = doj.isBefore(currentYearStart) ? currentYearStart : doj;
        long monthsWorked = ChronoUnit.MONTHS.between(effectiveDoj.withDayOfMonth(1), currentYearEnd.withDayOfMonth(1)) + 1;

        // For partial month, calculate number of days worked
        if (effectiveDoj.getDayOfMonth() > 1) {
            long daysInFirstMonth = ChronoUnit.DAYS.between(effectiveDoj, effectiveDoj.withDayOfMonth(effectiveDoj.lengthOfMonth()));
            double partialSalary = (employee.getSalary() / 30) * daysInFirstMonth;
            return (monthsWorked - 1) * employee.getSalary() + partialSalary;
        }

        return monthsWorked * employee.getSalary();
    }

    public double calculateTaxAmount(double yearlySalary) {
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

    public double calculateCessAmount(double yearlySalary) {
        if (yearlySalary > 2500000) {
            return (yearlySalary - 2500000) * 0.02;
        }
        return 0;
    }
}
