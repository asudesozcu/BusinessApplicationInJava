/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.example.task1;

import java.math.BigDecimal;
import java.util.Scanner;

/**
 *
 * @author m
 */
public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter number:");
        double d = scanner.nextDouble();
        double d2 = cube(d);
        System.out.println("Result is "+d2);
        
        BigDecimal number = new BigDecimal("33.33");
        BigDecimal number2 = number.multiply(new BigDecimal(2));
        
    }
    
    public static double cube(double n) {
        return n*n*n;
    }
}
