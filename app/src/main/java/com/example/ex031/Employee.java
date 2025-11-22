package com.example.ex031;

public abstract class Employee {
    private final int employeeId;
    private String name;
    protected double baseSalary;

    public Employee(int employeeId, String name, double baceSalary) {
        this.employeeId = employeeId;
        this.name = name;
        this.baseSalary = baceSalary;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public String getName() {
        return name;
    }

    public double getBaseSalary() {
        return baseSalary;
    }

    public abstract double calculateMonthlySalary();

    @Override
    public String toString() {

        return ("id: " + employeeId + ",name: " + name + ",base salary: " + baseSalary);
    }
}