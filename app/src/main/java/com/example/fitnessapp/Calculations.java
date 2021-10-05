package com.example.fitnessapp;
public class Calculations {

    public int add (int num1,int num2){
        return num1+num2;
    }

    public static float calculateBMI(String h, String w) {

        float heightValue = Float.parseFloat(h) / 100;
        float weightValue = Float.parseFloat(w);

        float bmi = weightValue / (heightValue * heightValue);

        return bmi;

    }

}
