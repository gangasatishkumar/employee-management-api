package com.example.employee.service;

import com.example.employee.constants.TaxServiceConstants;
import com.example.employee.model.Employee;
import com.example.employee.model.TaxDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.function.Predicate;

@Service
public class TaxCalculationService {

    public TaxDetails calculateTax(Employee employee) {
        validateEmployeeData(employee);  // Validate employee details before tax calculation

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

    /**
     * This method calculates the yearly salary based on the employee's Date of Joining (DOJ) and current financial year.
     */
    public double calculateYearlySalary(Employee employee) {
        LocalDate doj = employee.getDoj();
        LocalDate currentYearStart = LocalDate.of(LocalDate.now().getYear(), TaxServiceConstants.FINANCIAL_YEAR_START_MONTH, 1);
        LocalDate currentYearEnd = LocalDate.of(LocalDate.now().plusYears(1).getYear(), TaxServiceConstants.FINANCIAL_YEAR_END_MONTH, 31);

        if (doj.isAfter(currentYearEnd)) {
            return 0;  // Employee joined after the financial year
        }

        LocalDate effectiveDoj = doj.isBefore(currentYearStart) ? currentYearStart : doj;
        long monthsWorked = ChronoUnit.MONTHS.between(effectiveDoj.withDayOfMonth(1), currentYearEnd.withDayOfMonth(1)) + 1;

        // Calculate partial month salary if employee joined mid-month
        if (effectiveDoj.getDayOfMonth() > 1) {
            long daysInFirstMonth = ChronoUnit.DAYS.between(effectiveDoj, effectiveDoj.withDayOfMonth(effectiveDoj.lengthOfMonth()));
            double partialSalary = (employee.getSalary() / 30) * daysInFirstMonth;
            return (monthsWorked - 1) * employee.getSalary() + partialSalary;
        }

        return monthsWorked * employee.getSalary();
    }

    /**
     * This method calculates the tax amount based on the yearly salary.
     */
    public double calculateTaxAmount(double yearlySalary) {
        double tax = 0;

        if (yearlySalary > TaxServiceConstants.TAX_THRESHOLD_10_PERCENT) {
            tax += (yearlySalary - TaxServiceConstants.TAX_THRESHOLD_10_PERCENT) * TaxServiceConstants.TAX_RATE_20_PERCENT;
            yearlySalary = TaxServiceConstants.TAX_THRESHOLD_10_PERCENT;
        }
        if (yearlySalary > TaxServiceConstants.TAX_THRESHOLD_5_PERCENT) {
            tax += (yearlySalary - TaxServiceConstants.TAX_THRESHOLD_5_PERCENT) * TaxServiceConstants.TAX_RATE_10_PERCENT;
            yearlySalary = TaxServiceConstants.TAX_THRESHOLD_5_PERCENT;
        }
        if (yearlySalary > TaxServiceConstants.TAX_THRESHOLD_2_PERCENT) {
            tax += (yearlySalary - TaxServiceConstants.TAX_THRESHOLD_2_PERCENT) * TaxServiceConstants.TAX_RATE_5_PERCENT;
        }

        return tax;
    }

    /**
     * This method calculates the cess amount if the salary exceeds 25 lakhs.
     */
    public double calculateCessAmount(double yearlySalary) {
        if (yearlySalary > TaxServiceConstants.TAX_THRESHOLD_10_PERCENT * 2.5) {
            return (yearlySalary - TaxServiceConstants.TAX_THRESHOLD_10_PERCENT * 2.5) * TaxServiceConstants.CESS_RATE_2_PERCENT;
        }
        return 0;
    }

    /**
     * Validate the employee data to ensure it conforms to the business rules using lambda expressions.
     */
    private void validateEmployeeData(Employee employee) {
        // Lambda to validate salary
        Predicate<Double> isValidSalary = salary -> salary != null && salary > 0;

        // Lambda to validate DOJ
        Predicate<LocalDate> isValidDoj = doj -> doj != null && !doj.isAfter(LocalDate.now());

        // Validate Salary
        Optional.ofNullable(employee.getSalary())
                .filter(isValidSalary)
                .orElseThrow(() -> new IllegalArgumentException(TaxServiceConstants.INVALID_SALARY_MESSAGE));

        // Validate DOJ
        Optional.ofNullable(employee.getDoj())
                .filter(isValidDoj)
                .orElseThrow(() -> new IllegalArgumentException(TaxServiceConstants.INVALID_DOJ_MESSAGE));
    }
}
