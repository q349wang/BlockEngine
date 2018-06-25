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

}
