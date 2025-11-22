package com.example.ex031;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Employee> allEmployees =new ArrayList<>();

        allEmployees.add(new Manager(1,"Albert",40000,"Cyber-AndroidS",0.5));
        allEmployees.add(new Manager(2,"Itzchak",39000,"computer science",0.6));
        allEmployees.add(new Salesperson(3,"noa",35000,0.4,80000));
        allEmployees.add(new HourlyEmployee(4,"daniel",500,250,120));
        allEmployees.add(new HourlyEmployee(5,"Abraham",90,160,35));

        String text= "";
        for (Employee e: allEmployees){
            text +=e.toString()+"\n";
            text +="Monthly Salary: "+e.calculateMonthlySalary()+"\n"+"\n";

        }

        double totalBonuses=0;
        for (Employee e: allEmployees){
            if (e instanceof BonusEligible){
                totalBonuses +=((BonusEligible)e).calculateBonus();
            }
        }

        text += "total bonuses: " +totalBonuses;
        System.out.println(text);




    }
}