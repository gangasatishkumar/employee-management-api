package com.example.employee.constants;

public class TaxServiceConstants {

    // Default values
    public static final double DEFAULT_SALARY = 0.0;

    // Validation messages
    public static final String INVALID_SALARY_MESSAGE = "Salary must be a positive number.";
    public static final String INVALID_DOJ_MESSAGE = "Date of Joining (DOJ) must be a valid past or current date.";

    // Taxation thresholds
    public static final double TAX_THRESHOLD_10_PERCENT = 1000000;
    public static final double TAX_THRESHOLD_5_PERCENT = 500000;
    public static final double TAX_THRESHOLD_2_PERCENT = 250000;

    // Tax percentages
    public static final double TAX_RATE_20_PERCENT = 0.20;
    public static final double TAX_RATE_10_PERCENT = 0.10;
    public static final double TAX_RATE_5_PERCENT = 0.05;
    public static final double CESS_RATE_2_PERCENT = 0.02;

    // Financial year start and end dates
    public static final int FINANCIAL_YEAR_START_MONTH = 4;  // April
    public static final int FINANCIAL_YEAR_END_MONTH = 3;    // March (next year)
}
