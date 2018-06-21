package blockrpg;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class Position3DTests {

	@Test
	void testXDistancefrom() {
		Position3D point1 = new Position3D(2, 3, 0);
		Position3D point2 = new Position3D(-6, 7, 0);
		assertEquals(8, point2.xDistancefrom(point1));
	}

	@Test
	void testYDistancefrom() {
		Position3D point1 = new Position3D(2, 3, 0);
		Position3D point2 = new Position3D(-6, 7, 0);
		assertEquals(-4, point2.yDistancefrom(point1));
	}

	@Test
	void testZDistancefrom() {
		Position3D point1 = new Position3D(2, 3, 0);
		Position3D point2 = new Position3D(-6, 7, 0);
		assertEquals(0, point2.zDistancefrom(point1));
	}

	@Test
	void testTotDistanceFrom() {
		Position3D point1 = new Position3D(18, 4, 2);
		Position3D point2 = new Position3D(17, 6,4);
		assertEquals(3, point2.totDistanceFrom(point1));
	}

	@Test
	void testGetDirection() {
		Position3D point1 = new Position3D(18, 4, 2);
		Position3D point2 = new Position3D(17, 6,4);
		Vector3D expected = new Vector3D(1, -2, -2);
		assertEquals(expected, point2.getDirection(point1));
	}

}
