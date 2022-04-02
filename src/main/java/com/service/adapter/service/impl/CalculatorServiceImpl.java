package com.service.adapter.service.impl;

import com.service.adapter.component.MQComponent;
import com.service.adapter.service.CalculatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tempuri.CalculatorSoap;

import javax.xml.ws.soap.SOAPFaultException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class CalculatorServiceImpl implements CalculatorService {

    private ExecutorService executor = Executors.newFixedThreadPool(5);

    private CalculatorSoap calculatorSoap;

    @Autowired
    private MQComponent mQComponent;

    public CalculatorServiceImpl(CalculatorSoap calculatorSoap) {
        this.calculatorSoap = calculatorSoap;
    }

    private int add(int firstNumber, int secondNumber) {
        return calculatorSoap.add(firstNumber, secondNumber);
    }

    private int divide(int firstNumber, int secondNumber) {
        return calculatorSoap.divide(firstNumber, secondNumber);
    }

    private int multiply(int firstNumber, int secondNumber) {
        return calculatorSoap.multiply(firstNumber, secondNumber);
    }

    private int subtract(int firstNumber, int secondNumber) {
        return calculatorSoap.subtract(firstNumber, secondNumber);
    }

    @Override
    public void add(int firstNumber, int secondNumber, String correlationId) {
        executor.execute(
            () -> {
                try {
                    mQComponent.send(correlationId, Integer.toString(add(firstNumber, secondNumber)));
                } catch (SOAPFaultException e) {
                    mQComponent.send(correlationId, e.getMessage());
                }
            });

    }

    @Override
    public int getResoult4add(String correlationId) {
        return Integer.valueOf(mQComponent.receiver(correlationId));
    }

    @Override
    public void divide(int firstNumber, int secondNumber, String correlationId) {
        executor.execute(
            () -> {
                try {

                    mQComponent.send(correlationId, Integer.toString(divide(firstNumber, secondNumber)));
                } catch (SOAPFaultException e) {
                    mQComponent.send(correlationId, e.getMessage());
                }

            });
    }

    @Override
    public int getResoult4divide(String correlationId) {
        return Integer.valueOf(mQComponent.receiver(correlationId));
    }

    @Override
    public void multiply(int firstNumber, int secondNumber, String correlationId) {
        executor.execute(
            () -> {
                try {
                    mQComponent.send(correlationId, Integer.toString(multiply(firstNumber, secondNumber)));
                } catch (SOAPFaultException e) {
                    mQComponent.send(correlationId, e.getMessage());
                }

            });
    }

    @Override
    public int getResoult4multiply(String correlationId) {
        return Integer.valueOf(mQComponent.receiver(correlationId));
    }

    @Override
    public void subtract(int firstNumber, int secondNumber, String correlationId) {
        executor.execute(
            () -> {
                try {
                    mQComponent.send(correlationId, Integer.toString(subtract(firstNumber, secondNumber)));
                } catch (SOAPFaultException e) {
                    mQComponent.send(correlationId, e.getMessage());
                }

            });
    }

    @Override
    public int getResoult4subtract(String correlationId) {

        return Integer.valueOf(mQComponent.receiver(correlationId));
    }
}
