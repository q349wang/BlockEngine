package blockrpg;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

class Position2DTests {

	@Test
	void testXDistancefrom() {
		Position2D point1 = new Position2D(2, 3);
		Position2D point2 = new Position2D(-6, 7);
		assertEquals(8, point2.xDistancefrom(point1));
	}

	@Test
	void testYDistancefrom() {
		Position2D point1 = new Position2D(2, 3);
		Position2D point2 = new Position2D(-6, 7);
		assertEquals(-4, point2.yDistancefrom(point1));
	}


	@Test
	void testTotDistanceFrom() {
		Position2D point1 = new Position2D(20, 2);
		Position2D point2 = new Position2D(17, 6);
		assertEquals(5, point2.totDistanceFrom(point1));
	}

	@Test
	void testGetDirection() {
		Position2D point1 = new Position2D(18, 4);
		Position2D point2 = new Position2D(17, 6);
		Vector2D expected = new Vector2D(1, -2);
		assertEquals(expected, point2.getDirection(point1));
	}
	
	@Test
	void testAddX() {
		Position2D pos1 = new Position2D(1, 0);
		pos1.addX(2);
		Position2D expected = new Position2D(3, 0);
		assertEquals(expected, pos1);
	}

	@Test
	void testAddY() {
		Position2D pos1 = new Position2D(1, 0);
		pos1.addY(-2);
		Position2D expected = new Position2D(1, -2);
		assertEquals(expected, pos1);
	}

	@Test
	void testSetX() {
		Position2D pos1 = new Position2D(1, 0);
		pos1.setX(2);
		Position2D expected = new Position2D(2, 0);
		assertEquals(expected, pos1);
	}

	@Test
	void testSetY() {
		Position2D pos1 = new Position2D(1, 0);
		pos1.setY(-2);
		Position2D expected = new Position2D(1, -2);
		assertEquals(expected, pos1);
	}
	

	@Test
	void testRotate() {
		Position2D pos1 = new Position2D(1, 1);
		Position2D expected = new Position2D(-1, 1);
		assertEquals(expected, pos1.rotate(Math.PI / 2));
	}
	
	@Test
	void testToVec() {
		Position2D pos1 = new Position2D(1, 1);
		Vector2D expected = new Vector2D(1, 1);
		assertTrue(Arrays.equals(expected.getCoord(), pos1.getCoord()));
	}


}
