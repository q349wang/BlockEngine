package blockrpg;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class Coord3DTests {

	@Test
	void testGetAndSetX() {
		Coord3D test = new Coord3D(0, 0, 0);
		test.setX(2);
		assertEquals(2, test.getX());
	}

	@Test
	void testGetAndSetY() {
		Coord3D test = new Coord3D(0, 0, 0);
		test.setY(2);
		assertEquals(2, test.getY());
	}
	
	@Test
	void testGetAndSetZ() {
		Coord3D test = new Coord3D(0, 0, 0);
		test.setZ(2);
		assertEquals(2, test.getZ());
	}

	@Test
	void testAddX() {
		Coord3D test = new Coord3D(1, 1, 1);
		test.addX(2);
		assertEquals(3, test.getX());
	}

	@Test
	void testAddY() {
		Coord3D test = new Coord3D(1, 1, 1);
		test.addY(2);
		assertEquals(3, test.getY());
	}
	
	@Test
	void testAddZ() {
		Coord3D test = new Coord3D(1, 1, 1);
		test.addZ(2);
		assertEquals(3, test.getZ());
	}

}
