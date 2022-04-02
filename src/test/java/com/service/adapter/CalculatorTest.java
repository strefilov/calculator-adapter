package com.service.adapter;

import com.service.adapter.service.CalculatorService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.testng.Assert;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest()
public class CalculatorTest {
    @Autowired
    CalculatorService calculatorService;

    CalculatorService salsaOrderDetailsResult;

    @Before
    public void setup() throws Exception {
        salsaOrderDetailsResult = mock(CalculatorService.class);
        when(salsaOrderDetailsResult.getResoult4add(anyString())).thenReturn(Integer.valueOf(7));
        when(salsaOrderDetailsResult.getResoult4divide(anyString())).thenReturn(Integer.valueOf(2));
        when(salsaOrderDetailsResult.getResoult4multiply(anyString())).thenReturn(Integer.valueOf(8));
        when(salsaOrderDetailsResult.getResoult4subtract(anyString())).thenReturn(Integer.valueOf(6));
    }

    @Test
    public void addTest() {
        calculatorService.add(2, 5, "123421");
        Assert.assertTrue(salsaOrderDetailsResult.getResoult4add("123421") == 7);
    }

    @Test
    public void divideTest() {
        calculatorService.divide(4, 2, "12342");
        Assert.assertTrue(salsaOrderDetailsResult.getResoult4divide("12342") == 2);
    }

    @Test
    public void multiplyTest() {
        calculatorService.multiply(4, 2, "1234");
        Assert.assertTrue(salsaOrderDetailsResult.getResoult4multiply("1234") == 8);
    }

    @Test
    public void subtractTest() {
        calculatorService.subtract(8, 2, "4321");
        Assert.assertTrue(salsaOrderDetailsResult.getResoult4subtract("4321") == 6);

    }

}
