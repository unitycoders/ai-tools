package uk.me.webpigeon.util;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class TestVectorNorm {
	private final Double EPSILON = 0.0002;
	
	@Parameters
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {
				//already normalised vectors
				{0, 1, 0, 1},
				{0, -1, 0, -1},
				{1, 0, 1, 0},
				{-1, 0, -1, 0},
				
				//un-normialised vectors
				{0, 2, 0, 1},
				{0, -2, 0, -1},
				{2, 0, 1, 0},
				{-2, 0, -1, 0},
				
				// > 1 un-normalised vectors
				{0, 0.5, 0, 1},
				{0, -0.5, 0, -1},
				{0.5, 0, 1, 0},
				{-0.5, 0, -1, 0},
				
				// 45 degree vectors
				{1, 1, 0.7071067811865475, 0.7071067811865475},
				{-1, -1, -0.7071067811865475, -0.7071067811865475},
				{1, -1, 0.7071067811865475, -0.7071067811865475},
				{-1, 1, -0.7071067811865475, 0.7071067811865475},
		});
	}

    private double x;
    private double y;
    private double nX;
    private double nY;

    public TestVectorNorm(double x, double y, double nX, double nY) {
    	this.x = x;
    	this.y = y;
    	this.nX = nX;
    	this.nY = nY;
    }

    @Test
    public void test() {
    	Vector2D testVector = new Vector2D(x, y, true);
    	Vector2D expectedNormal = new Vector2D(nX, nY);
    	
    	testVector.normalise();
    	
    	assertEquals(expectedNormal.x, testVector.x, EPSILON);
    	assertEquals(expectedNormal.y, testVector.y, EPSILON);
    	assertEquals(expectedNormal.mag(), 1.0, EPSILON);
    }
	
}
