package com.example.fitnessapp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculationTest {

    private Calculations calculations;

    @BeforeEach
    public void setup(){
        calculations = new Calculations();
    }

    @Test
    public void testAddNumber(){
        int result = calculations.add(4,5);
        assertEquals(9,result);
    }

    @Test
    public void CalBmi(){
        float result = calculations.calculateBMI("165","60");
        assertEquals(22.0385684967041,result);
    }

}
