package blockrpg;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PlaneTests {

	@Test
	void testGetAndSetPlane() {
		Plane test = new Plane();
		Vector3D x = new Vector3D(1, 0, 0);
		Vector3D y = new Vector3D(0, 1, 0);
		Position3D pos = new Position3D(9, 0,5);
		Plane expected = new Plane(x, y, pos);

		test.setPos(pos.getCoord());
		test.setvecX(x.getCoord());
		test.setvecY(y.getCoord());
		
		assertEquals(expected.getNorm(), test.getNorm());
		assertEquals(expected.getD(), test.getD());
		assertEquals(expected.getPos(), test.getPos());
		assertEquals(expected.getVecX(), test.getVecX());
		assertEquals(expected.getVecY(), test.getVecY());
		assertEquals(expected, test);
	}
	
	@Test
	void testPlaceOnPlaneSimple() {
		Vector3D x = new Vector3D(1, 0, 0);
		Vector3D y = new Vector3D(0, 1, 0);
		Position3D pos = new Position3D();
		Plane testPlane = new Plane(x, y, pos);
		Position2D testPoint = new Position2D(1, 1);
		Position3D result = testPlane.placeOnPlane(testPoint);
		Position3D expected = new Position3D(1, 1, 0);

		assertEquals(expected, result);
	}

	@Test
	void testCloneConstructor() {
		Vector3D x = new Vector3D(1, 0, 0);
		Vector3D y = new Vector3D(0, 3.0 / 5, 4.0 / 5);
		Position3D pos = new Position3D(-1, 1, -1);
		Plane testPlane = new Plane(x, y, pos);
		Plane cloned = new Plane(testPlane);
		assertEquals(testPlane, cloned);
	}
	
	@Test
	void testCloneFunction() {
		Vector3D x = new Vector3D(1, 0, 0);
		Vector3D y = new Vector3D(0, 3.0 / 5, 4.0 / 5);
		Position3D pos = new Position3D(-1, 1, -1);
		Plane testPlane = new Plane(x, y, pos);
		Plane cloned = testPlane.clone();
		assertEquals(testPlane, cloned);
		assertFalse(testPlane == cloned);
		assertFalse(testPlane.getPos() == cloned.getPos());
		assertFalse(testPlane.getVecX() == cloned.getVecX());
		assertFalse(testPlane.getVecY() == cloned.getVecY());
	}

	@Test
	void testGetD() {
		Vector3D x = new Vector3D(1, 0, 0);
		Vector3D y = new Vector3D(0, 3.0 / 5, 4.0 / 5);
		Position3D pos = new Position3D(-1, 1, -1);
		Plane testPlane = new Plane(x, y, pos);
		assertEquals(-1.4, testPlane.getD());
	}

	@Test
	void testPlaceOnPlaneTypical() {
		Vector3D x = new Vector3D(1, 0, 0);
		Vector3D y = new Vector3D(0, 3.0 / 5, 4.0 / 5);
		Position3D pos = new Position3D(-1, 1, -1);
		Plane testPlane = new Plane(x, y, pos);
		Position2D testPoint = new Position2D(1, 1);
		Position3D result = testPlane.placeOnPlane(testPoint);
		Position3D expected = new Position3D(0, 1.6, -0.2);

		assertEquals(expected, result);
	}

	@Test
	void testIsParallelTrue() {
		Vector3D x = new Vector3D(1, 0, 0);
		Vector3D y = new Vector3D(0, 3.0 / 5, 4.0 / 5);
		Position3D pos = new Position3D(-1, 1, -1);
		Plane plane1 = new Plane(x, y, pos);

		Plane plane2 = new Plane(x.multiply(-2), y, pos);

		assertTrue(plane1.isParallel(plane2));
	}

	@Test
	void testIsParallelFalseVec() {
		Vector3D x = new Vector3D(1, 0, 0);
		Vector3D y = new Vector3D(0, 3.0 / 5, 4.0 / 5);
		Position3D pos = new Position3D(-1, 1, -1);
		Plane plane1 = new Plane(x, y, pos);

		x.setY(1);
		Plane plane2 = new Plane(x, y, pos);

		assertFalse(plane1.isParallel(plane2));
	}

	@Test
	void testIsParallelTrueDiffPos() {
		Vector3D x = new Vector3D(1, 0, 0);
		Vector3D y = new Vector3D(0, 3.0 / 5, 4.0 / 5);
		Position3D pos1 = new Position3D(-1, 1, -1);
		Plane plane1 = new Plane(x, y, pos1);

		Position3D pos2 = new Position3D(1, 3, 4);
		Plane plane2 = new Plane(x, y, pos2);

		assertTrue(plane1.isParallel(plane2));
	}
	
	@Test
	void testIsParallelTrueLine() {
		Vector3D x = new Vector3D(1, 0, 0);
		Vector3D y = new Vector3D(0, 3.0 / 5, 4.0 / 5);
		Position3D pos = new Position3D(-1, 1, -1);
		Plane testPlane = new Plane(x, y, pos);
		
		Line testLine1 = new Line(new Vector3D(1,3.0/5,4.0/5), new Position3D(0,1,-1));
		Line testLine2 = new Line(new Vector3D(1,3.0/5,4.0/5), new Position3D(-1,1,-1));
		assertTrue(testPlane.isParallel(testLine1));
		assertTrue(testPlane.isParallel(testLine2));
	}
	@Test
	void testIsParallelFalseLine() {
		Vector3D x = new Vector3D(1, 0, 0);
		Vector3D y = new Vector3D(0, 3.0 / 5, 4.0 / 5);
		Position3D pos = new Position3D(-1, 1, -1);
		Plane testPlane = new Plane(x, y, pos);
		
		Line testLine1 = new Line(new Vector3D(1,0,4.0/5), new Position3D(0,1,-1));
		Line testLine2 = new Line(new Vector3D(1,0,4.0/5), new Position3D(-1,1,-1));
		assertFalse(testPlane.isParallel(testLine1));
		assertFalse(testPlane.isParallel(testLine2));
	}

	@Test
	void testAddXYZ() {
		Vector3D x = new Vector3D(1, 0, 0);
		Vector3D y = new Vector3D(0, 3.0 / 5, 4.0 / 5);
		Position3D pos = new Position3D(-1, 1, -1);
		Plane plane1 = new Plane(x, y, pos);

		plane1.addX(2);
		plane1.addY(2);
		plane1.addZ(5);

		Position3D pos2 = new Position3D(1, 3, 4);
		Plane expected = new Plane(x, y, pos2);

		assertEquals(expected, plane1);
	}

	@Test
	void testSetXYZ() {
		Vector3D x = new Vector3D(1, 0, 0);
		Vector3D y = new Vector3D(0, 3.0 / 5, 4.0 / 5);
		Position3D pos = new Position3D(-1, 1, -1);
		Plane plane1 = new Plane(x, y, pos);

		plane1.setX(1);
		plane1.setY(3);
		plane1.setZ(4);

		Position3D pos2 = new Position3D(1, 3, 4);
		Plane expected = new Plane(x, y, pos2);

		assertEquals(expected, plane1);
	}

	@Test
	void testOnPlaneTrue() {
		Vector3D x = new Vector3D(1, 0, 0);
		Vector3D y = new Vector3D(0, 3.0 / 5, 4.0 / 5);
		Position3D pos = new Position3D(-1, 1, -1);
		Plane testPlane = new Plane(x, y, pos);
		Position3D testPoint = new Position3D(-1, 1, -1);

		assertTrue(testPlane.onPlane(testPoint));
	}

	@Test
	void testOnPlaneFalse() {
		Vector3D x = new Vector3D(1, 0, 0);
		Vector3D y = new Vector3D(0, 3.0 / 5, 4.0 / 5);
		Position3D pos = new Position3D(-1, 1, -1);
		Plane testPlane = new Plane(x, y, pos);
		Position3D testPoint = new Position3D(-4, 1, 0);

		assertFalse(testPlane.onPlane(testPoint));
	}
	
	@Test
	void testClone() {
		Vector3D x = new Vector3D(1, 0, 0);
		Vector3D y = new Vector3D(0, 3.0 / 5, 4.0 / 5);
		Position3D pos = new Position3D(-1, 1, -1);
		Plane plane1 = new Plane(x, y, pos);

		assertEquals(plane1, plane1.clone());
		assertFalse(plane1 == plane1.clone());
	}
	
	@Test
	void testGetIntersect() {
		Vector3D x = new Vector3D(1, 0, 0);
		Vector3D y = new Vector3D(0, 3.0 / 5, 4.0 / 5);
		Position3D pos = new Position3D(-1, 1, -1);
		Plane plane1 = new Plane(x, y, pos);

		Line line = new Line(new Vector3D(1,8, 6), new Position3D(-1, -7, -7));
		Position3D expected = new Position3D(0, 1, -1);
		assertEquals(expected, plane1.getIntersect(line)); 
	}
	
	@Test
	void testGetIntersectNone() {
		Vector3D x = new Vector3D(1, 0, 0);
		Vector3D y = new Vector3D(0, 3.0 / 5, 4.0 / 5);
		Position3D pos = new Position3D(-1, 1, -1);
		Plane plane1 = new Plane(x, y, pos);

		Line line = new Line(new Vector3D(2,3, 4), new Position3D(-1, 1, -1));
		assertEquals(null, plane1.getIntersect(line)); 
	}

}
