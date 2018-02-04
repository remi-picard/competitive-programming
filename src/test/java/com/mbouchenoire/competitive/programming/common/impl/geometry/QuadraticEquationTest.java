package com.mbouchenoire.competitive.programming.common.impl.geometry;

import com.mbouchenoire.competitive.programming.common.model.geometry.Complex;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;

public class QuadraticEquationTest {

    @Test
    public void testRealSolutions() {
        assertEquals(Collections.singletonList(new Complex(1, 0)), QuadraticEquation.solve(1, -2, 1));
        assertEquals(Collections.singletonList(new Complex(-2, 0)), QuadraticEquation.solve(0, 1, 2));
        //(x-2)*(x-1)=x*x-3x+2
        assertEquals(Arrays.asList(new Complex(1,0), new Complex(2,0)), QuadraticEquation.solve(1, -3, 2));
        assertEquals(Arrays.asList(new Complex(1,0), new Complex(2,0)), QuadraticEquation.solve(-1, 3, -2));
    }

    @Test
    public void imaginarySolutions() {
        assertEquals(Arrays.asList(new Complex(-1,-1), new Complex(-1,1)), QuadraticEquation.solve(1, 2, 2));
        assertEquals(Arrays.asList(new Complex(-1,-1), new Complex(-1,1)), QuadraticEquation.solve(-1, -2, -2));
    }
}
