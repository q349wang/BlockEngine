package blockrpg;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PlaneTests {

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
	void testisParallelTrue() {
		Vector3D x = new Vector3D(1, 0, 0);
		Vector3D y = new Vector3D(0, 3.0 / 5, 4.0 / 5);
		Position3D pos = new Position3D(-1, 1, -1);
		Plane plane1 = new Plane(x, y, pos);

		Plane plane2 = new Plane(x.multiply(-2), y, pos);
		
		assertTrue(plane1.isParallel(plane2));
	}
	
	@Test
	void testisParallelFalseVec() {
		Vector3D x = new Vector3D(1, 0, 0);
		Vector3D y = new Vector3D(0, 3.0 / 5, 4.0 / 5);
		Position3D pos = new Position3D(-1, 1, -1);
		Plane plane1 = new Plane(x, y, pos);
		
		x.setY(1);
		Plane plane2 = new Plane(x, y, pos);
		
		assertFalse(plane1.isParallel(plane2));
	}
	
	@Test
	void testisParallelFalsePos() {
		Vector3D x = new Vector3D(1, 0, 0);
		Vector3D y = new Vector3D(0, 3.0 / 5, 4.0 / 5);
		Position3D pos = new Position3D(-1, 1, -1);
		Plane plane1 = new Plane(x, y, pos);
		
		pos.setY(2);
		Plane plane2 = new Plane(x, y, pos);
		
		assertTrue(plane1.isParallel(plane2));
	}
	
	
	@Test
	void testOnPlaneTrue() {
		Vector3D x = new Vector3D(1, 0, 0);
		Vector3D y = new Vector3D(0, 3.0 / 5, 4.0 / 5);
		Position3D pos = new Position3D(-1, 1, -1);
		Plane testPlane = new Plane(x, y, pos);
		Position3D testPoint = new Position3D(-1,1,-1);
		
		assertTrue(testPlane.onPlane(testPoint));
	}
	
	
	@Test
	void testOnPlaneFalse() {
		Vector3D x = new Vector3D(1, 0, 0);
		Vector3D y = new Vector3D(0, 3.0 / 5, 4.0 / 5);
		Position3D pos = new Position3D(-1, 1, -1);
		Plane testPlane = new Plane(x, y, pos);
		Position3D testPoint = new Position3D(-4,1,-1);
		
		assertFalse(testPlane.onPlane(testPoint));
	}

}
