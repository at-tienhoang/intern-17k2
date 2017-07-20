package vn.asiantech.internship.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import vn.asiantech.internship.unittest.QuadraticEquation;
import vn.asiantech.internship.unittest.SolveQuadraticEquation;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * @author at-cuongcao
 * @version 1.0
 * @since 7/11/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class SolveQuadraticEquationTest {

    @Mock
    private QuadraticEquation mQuadraticEquation;
    
    @Test
    public void getDeltaTest() {
        //delta < 0
        double a = 5;
        double b = 2;
        double c = 1;
        Mockito.when(mQuadraticEquation.getDelta()).thenReturn(-16.0);
        assertEquals(SolveQuadraticEquation.getDelta(a, b, c), mQuadraticEquation.getDelta(), 0.001);

        //delta == 0
        a = 1;
        b = -2;
        c = 1;
        Mockito.when(mQuadraticEquation.getDelta()).thenReturn(0.0);
        assertEquals(SolveQuadraticEquation.getDelta(a, b, c), mQuadraticEquation.getDelta(), 0.001);

        //delta > 0
        a = 4;
        b = 5;
        c = 1;
        Mockito.when(mQuadraticEquation.getDelta()).thenReturn(9.0);
        assertEquals(SolveQuadraticEquation.getDelta(a, b, c), mQuadraticEquation.getDelta(), 0.001);
    }

    @Test
    public void solveTest() {
        //a == 0, b == 0, c == 0
        double a = 0;
        double b = 0;
        double c = 0;
        Mockito.when(mQuadraticEquation.getRoot()).thenReturn(null);
        assertArrayEquals(SolveQuadraticEquation.solve(a, b, c), mQuadraticEquation.getRoot(), 0.001);

        //a == 0, b != 0
        a = 0;
        b = 5;
        c = 10;
        double[] result = new double[]{-2};
        Mockito.when(mQuadraticEquation.getRoot()).thenReturn(result);
        assertArrayEquals(SolveQuadraticEquation.solve(a, b, c), mQuadraticEquation.getRoot(), 0.001);

        //delta < 0
        a = 5;
        b = 2;
        c = 1;
        Mockito.when(mQuadraticEquation.getRoot()).thenReturn(null);
        assertArrayEquals(SolveQuadraticEquation.solve(a, b, c), mQuadraticEquation.getRoot(), 0.001);

        //delta = 0
        a = 1;
        b = -2;
        c = 1;
        result = new double[]{1.0, 1.0};
        Mockito.when(mQuadraticEquation.getRoot()).thenReturn(result);
        assertArrayEquals(SolveQuadraticEquation.solve(a, b, c), mQuadraticEquation.getRoot(), 0.001);

        //delta > 0
        a = 1;
        b = 2;
        c = -3;
        result = new double[]{-3, 1};
        Mockito.when(mQuadraticEquation.getRoot()).thenReturn(result);
        assertArrayEquals(SolveQuadraticEquation.solve(a, b, c), mQuadraticEquation.getRoot(), 0.001);
    }
}
