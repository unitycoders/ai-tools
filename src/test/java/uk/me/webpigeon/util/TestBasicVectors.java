package uk.me.webpigeon.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TestBasicVectors {
	private final Double EPSILON = 0.0002;

	@Test
	public void testVectorConstructorWorks() {
		float expectX = 10;
		float expectY = 5;

		Vector2D v2 = new Vector2D(expectX, expectY);
		assertEquals(expectX, v2.x, EPSILON);
		assertEquals(expectY, v2.y, EPSILON);
		assertEquals(expectX, v2.getX(), EPSILON);
		assertEquals(expectY, v2.getY(), EPSILON);
	}

	@Test
	public void testCloneDoesNotAlterOrig() {
		float expectX = 10;
		float expectY = 5;

		Vector2D v1 = new Vector2D(expectX, expectY);
		Vector2D v2 = new Vector2D();
		v2.clone(v1);

		v2.multiply(10);

		assertEquals(expectX, v1.x, EPSILON);
		assertEquals(expectY, v1.y, EPSILON);
		assertEquals(expectX, v1.getX(), EPSILON);
		assertEquals(expectY, v1.getY(), EPSILON);

	}

}
