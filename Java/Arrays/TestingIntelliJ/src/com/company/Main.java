package com.company;

public class Main {

    public static void main(String[] args) {
        // write your code here
        System.out.println("Hallo Welt");

        for (int i = 0; i < 32; i++) {
            System.out.println(1 << i);
        }
        int[] numbers = {2, 5, -1, 6, 14, -7, -9};

        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = (numbers[i] < 0) ? 0 : numbers[i];
            System.out.println(numbers[i]);
        }

        for (int pos = 0; pos < 64; pos++) {
            System.out.println(Integer.MIN_VALUE >> pos);
        }
    }

    public static double average(int[] array) {
        int sum = 0;
        for (int i = 0; i < array.length; i++) {
            sum += array[i];
        }
        return sum / (double) (array.length);
    }

    public static boolean contains(int[] a, int wert) {
        for (int i = 0; i < a.length; i++) {
            if (a[i] == wert) {
                return true;
            }
        }
        return false;
    }

    public static double[] roundAll(double[] array) {
        double[] newArray = new double[array.length];
        for (int i = 0; i < array.length; i++) {
            newArray[i] = (double) Math.round(array[i]);
        }
        return newArray;
    }
}
