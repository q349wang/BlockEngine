package blockrpg;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

class Vector3DTests {
	

	@Test
	void testSetAndGetCoords() {
		double coords[] = { 1, 2, 3 };
		Vector3D testVec = new Vector3D();
		testVec.setCoord(coords);
		double testCoords[] = testVec.getCoord();
		assertTrue(Arrays.equals(coords, testCoords));
	}

	@Test
	void testGetLength() {
		Vector3D testVec = new Vector3D(2, 3, 6);
		assertEquals(7, testVec.getLength());
	}

	@Test
	void testNormalizeUnitVector() {
		Vector3D testVec = new Vector3D(1.0 / 3, 2.0 / 3, 2.0 / 3);
		testVec = testVec.normalize();
		assertEquals(1, testVec.getLength());
	}

	@Test
	void testNormalizeZeroVector() {
		Vector3D testVec = new Vector3D(0, 0, 0);
		testVec = testVec.normalize();
		assertEquals(0, testVec.getLength());
	}

	@Test
	void testNormalizeTypical() {
		Vector3D testVec = new Vector3D(2, 8, 4);
		testVec = testVec.normalize();
		assertEquals(1, testVec.getLength());
	}

	@Test
	void testMultiply() {
		Vector3D testVec = new Vector3D(1.0 / 3, 2.0 / 3, 2.0 / 3);
		double expected[] = { 1.0, 2.0, 2.0 };
		testVec = testVec.multiply(3.0);
		assertEquals(3, testVec.getLength());
		assertTrue(Arrays.equals(expected, testVec.getCoord()));
	}

	@Test
	void testDotPerpindicular() {
		Vector3D vec1 = new Vector3D(1, 2, 3);
		Vector3D vec2 = new Vector3D(1, 5, 7);
		vec2 = vec1.perp(vec2);
		double product = vec1.dot(vec2);
		assertEquals(0, product);
	}

	@Test
	void testDotTypical() {
		Vector3D vec1 = new Vector3D(1, 2, 3);
		Vector3D vec2 = new Vector3D(1, 5, 7);
		double product = vec1.dot(vec2);
		assertEquals(32, product);
	}

	@Test
	void testCrossTypical() {
		Vector3D vec1 = new Vector3D(-1, -2, 3);
		Vector3D vec2 = new Vector3D(4, 0, -8);
		Vector3D expected = new Vector3D(16, 4, 8);
		assertEquals(expected, vec1.cross(vec2));
		assertEquals(expected.multiply(-1), vec2.cross(vec1));
	}

	@Test
	void testCrossParallel() {
		Vector3D vec1 = new Vector3D(-1, -2, 3);
		Vector3D vec2 = new Vector3D(1, 2, -3);
		Vector3D expected = new Vector3D(0, 0, 0);
		assertEquals(expected, vec1.cross(vec2));
		assertEquals(expected, vec2.cross(vec1));
	}

	@Test
	void testProjTypical() {
		Vector3D vec1 = new Vector3D(1, 0, 3);
		Vector3D vec2 = new Vector3D(-1, 4, 2);
		Vector3D expected = new Vector3D(1.0 / 2, 0, 3.0 / 2);
		assertEquals(expected, vec1.proj(vec2));
	}

	@Test
	void testProjParallel() {
		Vector3D vec1 = new Vector3D(1, 0, 3);
		Vector3D vec2 = new Vector3D(2, 0, 6);
		Vector3D expected = new Vector3D(2, 0, 6);
		assertEquals(expected, vec1.proj(vec2));
	}

	@Test
	void testPerp() {
		Vector3D vec1 = new Vector3D(1, 0, 3);
		Vector3D vec2 = new Vector3D(-1, 4, 2);
		Vector3D expected = new Vector3D(-3.0 / 2, 4, 1.0 / 2);
		assertEquals(expected, vec1.perp(vec2));
	}

	@Test
	void testAdd() {
		Vector3D vec1 = new Vector3D(1, 0, 3);
		Vector3D vec2 = new Vector3D(-1, 4, 2);
		Vector3D expected = new Vector3D(0, 4, 5);
		assertEquals(expected, vec1.add(vec2));
	}

	@Test
	void testSubtract() {
		Vector3D vec1 = new Vector3D(1, 0, 3);
		Vector3D vec2 = new Vector3D(-1, 4, 2);
		Vector3D expected = new Vector3D(2, -4, 1);
		assertEquals(expected, vec1.subtract(vec2));
	}

	@Test
	void testAddX() {
		Vector3D vec1 = new Vector3D(1, 0, 3);
		vec1.addX(2);
		Vector3D expected = new Vector3D(3, 0, 3);
		assertEquals(expected, vec1);
	}

	@Test
	void testAddY() {
		Vector3D vec1 = new Vector3D(1, 0, 3);
		vec1.addY(-2);
		Vector3D expected = new Vector3D(1, -2, 3);
		assertEquals(expected, vec1);
	}

	@Test
	void testAddZ() {
		Vector3D vec1 = new Vector3D(1, 0, 3);
		vec1.addZ(2.5);
		Vector3D expected = new Vector3D(1, 0, 5.5);
		assertEquals(expected, vec1);
	}

	@Test
	void testSetX() {
		Vector3D vec1 = new Vector3D(1, 0, 3);
		vec1.setX(2);
		Vector3D expected = new Vector3D(2, 0, 3);
		assertEquals(expected, vec1);
	}

	@Test
	void testSetY() {
		Vector3D vec1 = new Vector3D(1, 0, 3);
		vec1.setY(-2);
		Vector3D expected = new Vector3D(1, -2, 3);
		assertEquals(expected, vec1);
	}

	@Test
	void testSetZ() {
		Vector3D vec1 = new Vector3D(1, 0, 3);
		vec1.setZ(2.5);
		Vector3D expected = new Vector3D(1, 0, 2.5);
		assertEquals(expected, vec1);
	}

	@Test
	void testGetAng() {
		Vector3D vec1 = new Vector3D(2,-4,-1);
		Vector3D vec2 = new Vector3D(0,5,2);
		assertEquals(2.6714087557718225, vec1.getAng(vec2), Vector3D.ERROR);
		assertEquals(2.6714087557718225, vec2.getAng(vec1), Vector3D.ERROR);
	}
	
	@Test
	void testRotateX() {
		Vector3D vec1 = new Vector3D(1, 0, 3);
		Vector3D expected = new Vector3D(1, -3, 0);
		assertEquals(expected, vec1.rotateX(Math.PI / 2));
	}

	@Test
	void testRotateY() {
		Vector3D vec1 = new Vector3D(1, 0, 3);
		Vector3D expected = new Vector3D(3, 0, -1);
		assertEquals(expected, vec1.rotateY(Math.PI / 2));
	}

	@Test
	void testRotateZ() {
		Vector3D vec1 = new Vector3D(1, 0, 3);
		Vector3D expected = new Vector3D(0, 1, 3);
		assertEquals(expected, vec1.rotateZ(Math.PI / 2));
	}

	@Test
	void testRotateAboutSameVector() {
		Vector3D vec1 = new Vector3D(1, 0, 0);
		Vector3D expected = new Vector3D(1, 0, 0);
		assertEquals(expected, vec1.rotate(Math.PI / 2, vec1));
	}

	@Test
	void testRotateTypical() {
		Vector3D vec1 = new Vector3D(-4, 4, 0);
		Vector3D axis = new Vector3D(1, 1, 0);
		Vector3D expected = new Vector3D(4, -4, 0);
		assertEquals(expected, vec1.rotate(Math.PI, axis));
	}

	@Test
	void testToPos() {
		Vector3D vec1 = new Vector3D(1, 1, 9);
		Position3D expected = new Position3D(1, 1, 9);
		assertTrue(Arrays.equals(expected.getCoord(), vec1.getCoord()));
	}

	@Test
	void testCloneFunction() {
		Vector3D vec1 = new Vector3D(1, 2, 2);
		Vector3D vec2 = vec1.clone();
		Vector3D expected = new Vector3D(1, 2, 2);
		assertEquals(3, vec2.getLength());
		assertEquals(expected, vec2);
	}
	
	@Test
	void testCloneConstructor() {
		Vector3D vec1 = new Vector3D(1, 2, 2);
		Vector3D vec2 = new Vector3D(vec1);
		Vector3D expected = new Vector3D(1, 2, 2);
		assertEquals(3, vec2.getLength());
		assertEquals(expected, vec2);
	}

	@Test
	void testIsParallelOrigin() {
		Vector3D origin = new Vector3D();
		Vector3D vec = new Vector3D(1, 0, 0);
		assertFalse(origin.isParallel(vec));
		assertFalse(vec.isParallel(origin));
	}

	@Test
	void testIsParallelTrue() {
		Vector3D vec1 = new Vector3D(-2, 0, 0);
		Vector3D vec2 = new Vector3D(1, 0, 0);
		assertTrue(vec1.isParallel(vec2));
		assertTrue(vec2.isParallel(vec1));
	}

	@Test
	void testIsParallelFalse() {
		Vector3D vec1 = new Vector3D(-2, 2, 0);
		Vector3D vec2 = new Vector3D(1, 0, 0);
		assertFalse(vec1.isParallel(vec2));
		assertFalse(vec2.isParallel(vec1));
	}

	@Test
	void testGetMultipleOriginAsThis() {
		Vector3D origin = new Vector3D();
		Vector3D vec = new Vector3D(1, 0, 0);

		assertThrows(IllegalArgumentException.class, () -> origin.getMultiple(vec));
	}

	@Test
	void testGetMultipleOriginAsOther() {
		Vector3D origin = new Vector3D();
		Vector3D vec = new Vector3D(1, 0, 0);

		assertEquals(0, vec.getMultiple(origin));
	}

	@Test
	void testGetMultipleNotParallel() {
		Vector3D vec1 = new Vector3D(0,1,0);
		Vector3D vec2 = new Vector3D(1, 0, 0);

		assertThrows(IllegalArgumentException.class, () -> vec1.getMultiple(vec2));
	}

	@Test
	void testGetMultipleParallel() {
		Vector3D vec1 = new Vector3D(3,0,0);
		Vector3D vec2 = new Vector3D(-6, 0, 0);

		assertEquals(-2, vec1.getMultiple(vec2));
	}
	
}
