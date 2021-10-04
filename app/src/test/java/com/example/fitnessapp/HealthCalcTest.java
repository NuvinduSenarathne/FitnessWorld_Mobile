package com.example.fitnessapp;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class HealthCalcTest {

    private HealthCalc healthCalc;

    @BeforeEach
    public void setup(){
        healthCalc = new HealthCalc();
    }

    //Test function by using boundary value testing Method
    @Test
    public void chechChol1() {
        String result = healthCalc.testCholesterol(0);
        assertEquals("Normal", result);
    }

    @Test
    public void chechChol2() {
        String result = healthCalc.testCholesterol(200);
        assertEquals("Attention", result);
    }

    @Test
    public void chechChol3() {
        String result = healthCalc.testCholesterol(240);
        assertEquals("Higher", result);
    }

    @Test
    public void checkBp1() {
        String result = healthCalc.testBloodPressure(0);
        assertEquals("Normal", result);
    }

    @Test
    public void checkBp2() {
        String result = healthCalc.testBloodPressure(80);
        assertEquals("Attention", result);
    }

    @Test
    public void checkBp3() {
        String result = healthCalc.testBloodPressure(90);
        assertEquals("Higher", result);
    }

    @Test
    public void checkDb1() {
        String result = healthCalc.testDiabetes(0);
        assertEquals("Normal", result);
    }

    @Test
    public void checkDb2() {
        String result = healthCalc.testDiabetes(100);
        assertEquals("Attention", result);
    }

    @Test
    public void checkDb3() {
        String result = healthCalc.testDiabetes(125);
        assertEquals("Higher", result);
    }

    //Test function by using Equivalence partition testing Method
    @Test
    public void chechChol4() {
        String result = healthCalc.testCholesterol(-2);
        assertEquals("Invalid", result);
    }

    @Test
    public void chechChol5() {
        String result = healthCalc.testCholesterol(100);
        assertEquals("Normal", result);
    }

    @Test
    public void chechChol6() {
        String result = healthCalc.testCholesterol(220);
        assertEquals("Attention", result);
    }

    @Test
    public void chechChol7() {
        String result = healthCalc.testCholesterol(300);
        assertEquals("Higher", result);
    }

    @Test
    public void checkBp4() {
        String result = healthCalc.testBloodPressure(-1);
        assertEquals("Invalid", result);
    }

    @Test
    public void checkBp5() {
        String result = healthCalc.testBloodPressure(50);
        assertEquals("Normal", result);
    }

    @Test
    public void checkBp6() {
        String result = healthCalc.testBloodPressure(85);
        assertEquals("Attention", result);
    }

    @Test
    public void checkBp7() {
        String result = healthCalc.testBloodPressure(100);
        assertEquals("Higher", result);
    }

    @Test
    public void checkDb4() {
        String result = healthCalc.testDiabetes(-1);
        assertEquals("Invalid", result);
    }

    @Test
    public void checkDb5() {
        String result = healthCalc.testDiabetes(50);
        assertEquals("Normal", result);
    }

    @Test
    public void checkDb6() {
        String result = healthCalc.testDiabetes(110);
        assertEquals("Attention", result);
    }

    @Test
    public void checkDb7() {
        String result = healthCalc.testDiabetes(150);
        assertEquals("Higher", result);
    }

}
