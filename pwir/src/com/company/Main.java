package com.company;

public class Main {

    public static void main(String[] args) {
	    Integral integer = new Integral();
        Function function = new MySinus();
        Double s = integer.trapezoidalRulle(0.0,1.0,1000,function);
        Double s2 = integer.parallerTrapezoidalRulle(0.0,1.0,1000,function);
        System.out.println(s);
        System.out.println(s2);

    }
}
