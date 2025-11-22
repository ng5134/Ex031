package com.example.ex031;

public class HourlyEmployee extends Employee {
    private double hoursWorked;
    private double hourlyRate;

    public HourlyEmployee(int id,String name,double baseSalary,double hoursWorked,double hourlyRate){
        super (id,name,baseSalary);
        this.hoursWorked=hoursWorked;
        this.hourlyRate=hourlyRate;

    }
    @Override
    public double calculateMonthlySalary(){
        return (hoursWorked *hourlyRate);
    }

}
