package com.example.ex031;

public class Salesperson extends Employee implements BonusEligible{
    private double salesCommission;
    private double totalSales;

    public  Salesperson(int id, String name,double baseSalary,double salesCommission,double totalSales){
        super(id,name,baseSalary);
        this.salesCommission= salesCommission;
        this.totalSales= totalSales;
    }
    @Override
    public double calculateMonthlySalary(){
        return (hoursWorked *hourlyRate);
    }


}

