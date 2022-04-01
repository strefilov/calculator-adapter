package com.service.adapter.service.impl;

import com.service.adapter.client.CalculatorSOAPClient;
import com.service.adapter.component.MQComponent;
import com.service.adapter.exception.SoapResultException;
import com.service.adapter.model.error.Error;
import com.service.adapter.service.CalculatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.service.adapter.wsdl.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class CalculatorServiceImpl implements CalculatorService {

    private static final String NO_RESULT_IN_RESPONSE_FROM_SOAP_CALCULATOR_SERVICE = "No result in response from SOAP calculator service";

    private ExecutorService executor = Executors.newFixedThreadPool(5);

    private CalculatorSOAPClient calculatorSOAPClient;

    @Autowired
    private MQComponent mQComponent;

    public CalculatorServiceImpl(CalculatorSOAPClient calculatorSOAPClient) {
        this.calculatorSOAPClient = calculatorSOAPClient;
    }

    private int add(int firstNumber, int secondNumber) {
        Add add = new Add();
        add.setIntA(firstNumber);
        add.setIntB(secondNumber);
        AddResponse response = calculatorSOAPClient.add(add);

        if (response == null)
            throw new SoapResultException(
                new Error(NO_RESULT_IN_RESPONSE_FROM_SOAP_CALCULATOR_SERVICE)
            );

        return response.getAddResult();
    }

    private int divide(int firstNumber, int secondNumber) {
        Divide divide = new Divide();
        divide.setIntA(firstNumber);
        divide.setIntB(secondNumber);
        DivideResponse response = calculatorSOAPClient.divide(divide);

        if (response == null)
            throw new SoapResultException(
                new Error(NO_RESULT_IN_RESPONSE_FROM_SOAP_CALCULATOR_SERVICE)
            );

        return response.getDivideResult();
    }

    private int multiply(int firstNumber, int secondNumber) {
        Multiply multiply = new Multiply();
        multiply.setIntA(firstNumber);
        multiply.setIntB(secondNumber);
        MultiplyResponse response = calculatorSOAPClient.multiply(multiply);

        if (response == null)
            throw new SoapResultException(
                new Error(NO_RESULT_IN_RESPONSE_FROM_SOAP_CALCULATOR_SERVICE)
            );

        return response.getMultiplyResult();
    }

    private int subtract(int firstNumber, int secondNumber) {
        Subtract subtract = new Subtract();
        subtract.setIntA(firstNumber);
        subtract.setIntB(secondNumber);
        SubtractResponse response = calculatorSOAPClient.subtract(subtract);

        if (response == null)
            throw new SoapResultException(
                new Error(NO_RESULT_IN_RESPONSE_FROM_SOAP_CALCULATOR_SERVICE)
            );

        return response.getSubtractResult();
    }

    @Override
    public void add(int firstNumber, int secondNumber, String correlationId) {
        executor.execute(
            new Runnable() {
                @Override
                public void run() {
                    mQComponent.send(correlationId, Integer.toString(add(firstNumber, secondNumber)));
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
            new Runnable() {
                @Override
                public void run() {
                    mQComponent.send(correlationId, Integer.toString(divide(firstNumber, secondNumber)));
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
            new Runnable() {
                @Override
                public void run() {
                    mQComponent.send(correlationId, Integer.toString(multiply(firstNumber, secondNumber)));
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
            new Runnable() {
                @Override
                public void run() {
                    mQComponent.send(correlationId, Integer.toString(subtract(firstNumber, secondNumber)));
                }
            });
    }

    @Override
    public int getResoult4subtract(String correlationId) {

        return Integer.valueOf(mQComponent.receiver(correlationId));
    }
}
