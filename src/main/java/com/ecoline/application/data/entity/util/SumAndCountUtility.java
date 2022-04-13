package com.ecoline.application.data.entity.util;


public class SumAndCountUtility {
    double sumOfDeviation;
    int countOfDeviation;

    public SumAndCountUtility() {
    }

    public SumAndCountUtility(double sumOfDeviation, long countOfDeviation) {
        this.sumOfDeviation = sumOfDeviation;
        this.countOfDeviation = (int) countOfDeviation;
    }

    public double getSumOfDeviation() {
        return sumOfDeviation;
    }

    public void setSumOfDeviation(int sumOfDeviation) {
        this.sumOfDeviation = sumOfDeviation;
    }

    public int getCountOfDeviation() {
        return countOfDeviation;
    }

    public void setCountOfDeviation(int countOfDeviation) {
        this.countOfDeviation = countOfDeviation;
    }
}
