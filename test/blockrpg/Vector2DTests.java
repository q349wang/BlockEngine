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
		Vector2D expected = new Vector2D(3.0, 4.0);
		testVec = testVec.multiply(3.0);
		assertTrue(Math.abs(5 - testVec.getLength()) < Coord3D.ERROR);
		assertEquals(expected, testVec);
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
	void testGetAng() {
		Vector2D vec1 = new Vector2D(3, 0);
		Vector2D vec2 = new Vector2D(2, 2);
		assertEquals(Math.PI / 4, vec1.getAng(vec2), Vector2D.ERROR);
		assertEquals(Math.PI / 4, vec2.getAng(vec1), Vector2D.ERROR);
	}

	@Test
	void testRotate() {
		Vector2D vec1 = new Vector2D(1, 1);
		vec1.rotate(Math.PI / 2);
		Vector2D expected = new Vector2D(-1, 1);
		assertEquals(expected, vec1);
	}

	@Test
	void testToPos() {
		Vector2D vec1 = new Vector2D(1, 1);
		Position2D expected = new Position2D(1, 1);
		assertTrue(Arrays.equals(expected.getCoord(), vec1.getCoord()));
	}

	@Test
	void testCloneFunction() {
		Vector2D vec1 = new Vector2D(3, 4);
		Vector2D vec2 = vec1.clone();
		Vector2D expected = new Vector2D(3, 4);
		assertEquals(5, vec2.getLength());
		assertEquals(expected, vec2);
		assertFalse(expected == vec2);
	}

	@Test
	void testCloneConstructor() {
		Vector2D vec1 = new Vector2D(3, 4);
		Vector2D vec2 = new Vector2D(vec1);
		Vector2D expected = new Vector2D(3, 4);
		assertEquals(5, vec2.getLength());
		assertEquals(expected, vec2);
	}

	@Test
	void testIsParallelTrue() {
		Vector2D vec1 = new Vector2D(-2, 0);
		Vector2D vec2 = new Vector2D(1, 0);
		assertTrue(vec1.isParallel(vec2));
		assertTrue(vec2.isParallel(vec1));
	}

	@Test
	void testIsParallelFalse() {
		Vector2D vec1 = new Vector2D(-2, 2);
		Vector2D vec2 = new Vector2D(1, 0);
		assertFalse(vec1.isParallel(vec2));
		assertFalse(vec2.isParallel(vec1));
	}

	@Test
	void testIsParallelOneXZero() {
		Vector2D vec1 = new Vector2D(0, 2);
		Vector2D vec2 = new Vector2D(1, 2);
		assertFalse(vec1.isParallel(vec2));
		assertFalse(vec2.isParallel(vec1));
	}

	@Test
	void testIsParallelBothXZero() {
		Vector2D vec1 = new Vector2D(0, 2);
		Vector2D vec2 = new Vector2D(0, 1);
		assertTrue(vec1.isParallel(vec2));
		assertTrue(vec2.isParallel(vec1));
	}

	@Test
	void testIsParallelOneYZero() {
		Vector2D vec1 = new Vector2D(7, 0);
		Vector2D vec2 = new Vector2D(1, 2);
		assertFalse(vec1.isParallel(vec2));
		assertFalse(vec2.isParallel(vec1));
	}

	@Test
	void testIsParallelBothYZero() {
		Vector2D vec1 = new Vector2D(6, 0);
		Vector2D vec2 = new Vector2D(1, 0);
		assertTrue(vec1.isParallel(vec2));
		assertTrue(vec2.isParallel(vec1));
	}

	@Test
	void testGetMultipleOriginAsThis() {
		Vector2D origin = new Vector2D();
		Vector2D vec = new Vector2D(1, 0);

		assertThrows(IllegalArgumentException.class, () -> origin.getMultiple(vec));
	}

	@Test
	void testGetMultipleOriginAsOther() {
		Vector2D origin = new Vector2D();
		Vector2D vec = new Vector2D(1, 0);

		assertEquals(0, vec.getMultiple(origin));
	}

	@Test
	void testGetMultipleNotParallel() {
		Vector2D vec1 = new Vector2D(0, 1);
		Vector2D vec2 = new Vector2D(1, 0);

		assertThrows(IllegalArgumentException.class, () -> vec1.getMultiple(vec2));
	}

	@Test
	void testGetMultipleParallel() {
		Vector2D vec1 = new Vector2D(3, 0);
		Vector2D vec2 = new Vector2D(-6, 0);

		assertEquals(-2, vec1.getMultiple(vec2));
	}

}
