package blockrpg;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

class Vector2DTests {

	@Test
	void testSetAndGetCoords() {
		double coords[] = { 1, 2 };
		Vector2D testVec = new Vector2D();
		testVec.setCoord(coords);
		double testCoords[] = testVec.getCoord();
		assertTrue(Arrays.equals(coords, testCoords));
	}

	@Test
	void testGetLength() {
		Vector2D testVec = new Vector2D(3, 4);
		assertEquals(5, testVec.getLength());
	}

	@Test
	void testNormalizeUnitVector() {
		Vector2D testVec = new Vector2D(3.0 / 5, 4.0 / 5);
		testVec = testVec.normalize();
		assertEquals(1, testVec.getLength());
	}

	@Test
	void testNormalizeZeroVector() {
		Vector2D testVec = new Vector2D(0, 0);
		testVec = testVec.normalize();
		assertEquals(0, testVec.getLength());
	}

	@Test
	void testNormalizeTypical() {
		Vector2D testVec = new Vector2D(2, 8);
		testVec = testVec.normalize();
		assertEquals(1, testVec.getLength());
	}

	@Test
	void testMultiply() {
		Vector2D testVec = new Vector2D(3.0 / 3, 4.0 / 3);
		double expected[] = { 3.0, 4.0 };
		testVec = testVec.multiply(3.0);
		assertEquals(5, testVec.getLength());
		assertTrue(Arrays.equals(expected, testVec.getCoord()));
	}

	@Test
	void testDotPerpindicular() {
		Vector2D vec1 = new Vector2D(1, 2);
		Vector2D vec2 = new Vector2D(2, -1);
		vec2 = vec1.perp(vec2);
		double product = vec1.dot(vec2);
		assertEquals(0, product);
	}

	@Test
	void testDotTypical() {
		Vector2D vec1 = new Vector2D(1, 2);
		Vector2D vec2 = new Vector2D(2, 1);
		double product = vec1.dot(vec2);
		assertEquals(4, product);
	}

	@Test
	void testProjTypical() {
		Vector2D vec1 = new Vector2D(2, 4);
		Vector2D vec2 = new Vector2D(-1, 5);
		Vector2D expected = new Vector2D(9.0 / 5, 18.0 / 5);
		assertEquals(expected, vec1.proj(vec2));
	}

	@Test
	void testProjParallel() {
		Vector2D vec1 = new Vector2D(1, 0);
		Vector2D vec2 = new Vector2D(2, 0);
		Vector2D expected = new Vector2D(2, 0);
		assertEquals(expected, vec1.proj(vec2));
	}

	@Test
	void testPerp() {
		Vector2D vec1 = new Vector2D(2, 4);
		Vector2D vec2 = new Vector2D(-1, 5);
		Vector2D expected = new Vector2D(-14.0 / 5, 7.0 / 5);
		assertEquals(expected, vec1.perp(vec2));
	}

	@Test
	void testAdd() {
		Vector2D vec1 = new Vector2D(1, 0);
		Vector2D vec2 = new Vector2D(-1, 4);
		Vector2D expected = new Vector2D(0, 4);
		assertEquals(expected, vec1.add(vec2));
	}

	@Test
	void testSubtract() {
		Vector2D vec1 = new Vector2D(1, 0);
		Vector2D vec2 = new Vector2D(-1, 4);
		Vector2D expected = new Vector2D(2, -4);
		assertEquals(expected, vec1.subtract(vec2));
	}

	@Test
	void testAddX() {
		Vector2D vec1 = new Vector2D(1, 0);
		vec1.addX(2);
		Vector2D expected = new Vector2D(3, 0);
		assertEquals(expected, vec1);
	}

	@Test
	void testAddY() {
		Vector2D vec1 = new Vector2D(1, 0);
		vec1.addY(-2);
		Vector2D expected = new Vector2D(1, -2);
		assertEquals(expected, vec1);
	}

	@Test
	void testSetX() {
		Vector2D vec1 = new Vector2D(1, 0);
		vec1.setX(2);
		Vector2D expected = new Vector2D(2, 0);
		assertEquals(expected, vec1);
	}

	@Test
	void testSetY() {
		Vector2D vec1 = new Vector2D(1, 0);
		vec1.setY(-2);
		Vector2D expected = new Vector2D(1, -2);
		assertEquals(expected, vec1);
	}

	@Test
	void testRotate() {
		Vector2D vec1 = new Vector2D(1, 1);
		Vector2D expected = new Vector2D(-1, 1);
		assertEquals(expected, vec1.rotate(Math.PI / 2));
	}
	
	@Test
	void testToPos() {
		Vector2D vec1 = new Vector2D(1, 1);
		Position2D expected = new Position2D(1, 1);
		assertTrue(Arrays.equals(expected.getCoord(), vec1.getCoord()));
	}
	
	@Test
	void testClone() {
		Vector2D vec1 = new Vector2D(3,4);
		Vector2D vec2 = vec1.clone();
		Vector2D expected = new Vector2D(3,4);
		assertEquals(5, vec2.getLength());
		assertEquals(expected, vec2);
	}

}
