package com.example.ex031;

public class Manager extends Employee implements BonusEligible{
    private String department;
    private double managementBonusPercentage;

    public Manager(int id, String name,double baseSalary,String department, double managementBonusPercentage){
        super(id,name,baseSalary);
        this.department=department;
        this.managementBonusPercentage=managementBonusPercentage;

    }
    @Override
    public double calculateMonthlySalary(){
        return (baseSalary);
    }
    @Override
    public double calculateBonus(){
        return(baseSalary * managementBonusPercentage);
    }
    @Override
    public String toString() {
        return (super.toString()+",department: "+department);
    }
}
