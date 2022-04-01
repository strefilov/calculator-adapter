package com.service.adapter.service;


public interface CalculatorService {

    void add(int firstNumber, int secondNumber,String correlationId);
    int getResoult4add(String correlationId);
    void divide(int firstNumber, int secondNumber,String correlationId);
    int getResoult4divide(String correlationId);
    void multiply(int firstNumber, int secondNumber,String correlationId);
    int getResoult4multiply(String correlationId);
    void subtract(int firstNumber, int secondNumber,String correlationId);
    int getResoult4subtract(String correlationId);
}
