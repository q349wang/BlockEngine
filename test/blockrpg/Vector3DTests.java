package blockrpg;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

class Vector3DTests {
	
	@Test
	void testSetAndGetCoords() {
		double coords[] = {1,2,3};
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
		Vector3D testVec = new Vector3D(1.0/3,2.0/3,2.0/3);
		testVec = testVec.normalize();
		assertEquals(1, testVec.getLength());
	}
	
	@Test
	void testNormalizeZeroVector() {
		Vector3D testVec = new Vector3D(0,0,0);
		testVec = testVec.normalize();
		assertEquals(0, testVec.getLength());
	}
	
	@Test
	void testNormalizeTypical() {
		Vector3D testVec = new Vector3D(2,8,4);
		testVec = testVec.normalize();
		assertEquals(1, testVec.getLength());
	}

	@Test
	void testMultiply() {
		Vector3D testVec = new Vector3D(1.0/3,2.0/3,2.0/3);
		double expected[] = {1.0, 2.0,2.0};
		testVec = testVec.multiply(3.0);
		assertEquals(3, testVec.getLength());
		assertTrue(Arrays.equals(expected, testVec.getCoord()));
	}

	@Test
	void testDotPerpindicular() {
		Vector3D vec1 = new Vector3D(1,2,3);
		Vector3D vec2 = new Vector3D(1,5,7);
		vec2 = vec1.perp(vec2);
		double product = vec1.dot(vec2);
		assertEquals(0, product);
	}
	
	@Test
	void testDotTypical() {
		Vector3D vec1 = new Vector3D(1,2,3);
		Vector3D vec2 = new Vector3D(1,5,7);
		double product = vec1.dot(vec2);
		assertEquals(32, product);
	}

	@Test
	void testCrossTypical() {
		Vector3D vec1 = new Vector3D(-1,-2,3);
		Vector3D vec2 = new Vector3D(4,0,-8);
		Vector3D expected = new Vector3D(16, 4, 8);
		assertEquals(expected, vec1.cross(vec2));
		assertEquals(expected.multiply(-1), vec2.cross(vec1));
	}
	
	@Test
	void testCrossParallel() {
		Vector3D vec1 = new Vector3D(-1,-2,3);
		Vector3D vec2 = new Vector3D(1,2,-3);
		Vector3D expected = new Vector3D(0, 0, 0);
		assertEquals(expected, vec1.cross(vec2));
		assertEquals(expected, vec2.cross(vec1));
	}

	@Test
	void testProjTypical() {
		Vector3D vec1 = new Vector3D(1,0,3);
		Vector3D vec2 = new Vector3D(-1,4,2);
		Vector3D expected = new Vector3D(1.0/2, 0, 3.0/2);
		assertEquals(expected, vec1.proj(vec2));
	}
	
	@Test
	void testProjParallel() {
		Vector3D vec1 = new Vector3D(1,0,3);
		Vector3D vec2 = new Vector3D(2,0,6);
		Vector3D expected = new Vector3D(2, 0, 6);
		assertEquals(expected, vec1.proj(vec2));
	}

	@Test
	void testPerp() {
		Vector3D vec1 = new Vector3D(1,0,3);
		Vector3D vec2 = new Vector3D(-1,4,2);
		Vector3D expected = new Vector3D(-3.0/2, 4, 1.0/2);
		assertEquals(expected, vec1.perp(vec2));
	}

	@Test
	void testRotateX() {
		Vector3D vec1 = new Vector3D(1,0,3);
		Vector3D expected = new Vector3D(1, -3, 0);
		assertEquals(expected, vec1.rotateX(Math.PI/2));
	}

	@Test
	void testRotateY() {
		Vector3D vec1 = new Vector3D(1,0,3);
		Vector3D expected = new Vector3D(3, 0, -1);
		assertEquals(expected, vec1.rotateY(Math.PI/2));
	}

	@Test
	void testRotateZ() {
		Vector3D vec1 = new Vector3D(1,0,3);
		Vector3D expected = new Vector3D(0, 1, 3);
		assertEquals(expected, vec1.rotateZ(Math.PI/2));
	}

	@Test
	void testRotateAboutSameVector() {
		Vector3D vec1 = new Vector3D(1,0,0);
		Vector3D expected = new Vector3D(1,0,0);
		assertEquals(expected, vec1.rotate(Math.PI/2, vec1));
	}
	
	@Test
	void testRotateTypical() {
		Vector3D vec1 = new Vector3D(-4,4,0);
		Vector3D axis = new Vector3D(1,1,0);
		Vector3D expected = new Vector3D(4, -4, 0);
		assertEquals(expected, vec1.rotate(Math.PI, axis));
	}

}
