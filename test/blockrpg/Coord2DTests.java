package blockrpg;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class Coord2DTests {

	@Test
	void testGetAndSetX() {
		Coord2D test = new Coord2D(0,0);
		test.setX(2);
		assertEquals(2, test.getX());
	}

	@Test
	void testGetAndSetY() {
		Coord2D test = new Coord2D(0,0);
		test.setY(2);
		assertEquals(2, test.getY());
	}

	@Test
	void testAddX() {
		Coord2D test = new Coord2D(1,1);
		test.addX(2);
		assertEquals(3, test.getX());
	}

	@Test
	void testAddY() {
		Coord2D test = new Coord2D(1,1);
		test.addY(2);
		assertEquals(3, test.getY());
	}
	
	@Test
	void testClone() {
		Coord2D test = new Coord2D(1,1);
		assertFalse(test == test.clone());
		assertEquals(test, test.clone());
	}
}
