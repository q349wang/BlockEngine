package blockrpg;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

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
	

	@Test
	void testAdd() {
		Position3D pos1 = new Position3D(1, 0, 3);
		Position3D pos2 = new Position3D(-1, 4, 2);
		Position3D expected = new Position3D(0, 4, 5);
		assertEquals(expected, pos1.add(pos2));
	}

	@Test
	void testSubtract() {
		Position3D pos1 = new Position3D(1, 0, 3);
		Position3D pos2 = new Position3D(-1, 4, 2);
		Position3D expected = new Position3D(2, -4, 1);
		assertEquals(expected, pos1.subtract(pos2));
	}

	@Test
	void testAddX() {
		Position3D pos1 = new Position3D(1, 0, 3);
		pos1.addX(2);
		Position3D expected = new Position3D(3, 0, 3);
		assertEquals(expected, pos1);
	}

	@Test
	void testAddY() {
		Position3D pos1 = new Position3D(1, 0, 3);
		pos1.addY(-2);
		Position3D expected = new Position3D(1, -2, 3);
		assertEquals(expected, pos1);
	}

	@Test
	void testAddZ() {
		Position3D pos1 = new Position3D(1, 0, 3);
		pos1.addZ(2.5);
		Position3D expected = new Position3D(1, 0, 5.5);
		assertEquals(expected, pos1);
	}

	@Test
	void testSetX() {
		Position3D pos1 = new Position3D(1, 0, 3);
		pos1.setX(2);
		Position3D expected = new Position3D(2, 0, 3);
		assertEquals(expected, pos1);
	}

	@Test
	void testSetY() {
		Position3D pos1 = new Position3D(1, 0, 3);
		pos1.setY(-2);
		Position3D expected = new Position3D(1, -2, 3);
		assertEquals(expected, pos1);
	}

	@Test
	void testSetZ() {
		Position3D pos1 = new Position3D(1, 0, 3);
		pos1.setZ(2.5);
		Position3D expected = new Position3D(1, 0, 2.5);
		assertEquals(expected, pos1);
	}

	@Test
	void testRotateX() {
		Position3D pos1 = new Position3D(1, 0, 3);
		Position3D expected = new Position3D(1, -3, 0);
		assertEquals(expected, pos1.rotateX(Math.PI / 2));
	}

	@Test
	void testRotateY() {
		Position3D pos1 = new Position3D(1, 0, 3);
		Position3D expected = new Position3D(3, 0, -1);
		assertEquals(expected, pos1.rotateY(Math.PI / 2));
	}

	@Test
	void testRotateZ() {
		Position3D pos1 = new Position3D(1, 0, 3);
		Position3D expected = new Position3D(0, 1, 3);
		assertEquals(expected, pos1.rotateZ(Math.PI / 2));
	}

	@Test
	void testRotateAboutSameVector() {
		Position3D pos1 = new Position3D(1, 0, 0);
		Position3D expected = new Position3D(1, 0, 0);
		assertEquals(expected, pos1.rotate(Math.PI / 2, pos1.toVec()));
	}

	@Test
	void testRotateTypical() {
		Position3D pos1 = new Position3D(-4, 4, 0);
		Vector3D axis = new Vector3D(1, 1, 0);
		Position3D expected = new Position3D(4, -4, 0);
		assertEquals(expected, pos1.rotate(Math.PI, axis));
	}
	
	@Test
	void testToVec() {
		Position3D pos1 = new Position3D(1, 1,9);
		Vector3D expected = new Vector3D(1, 1,9);
		assertTrue(Arrays.equals(expected.getCoord(), pos1.getCoord()));
	}
	
	@Test
	void testCloneFunction() {
		Position3D pos1 = new Position3D(1,1,1);
		Position3D pos2 = pos1.clone();
		Position3D expected = new Position3D(1,1,1);
		assertEquals(expected, pos2);
		assertFalse(expected == pos2);
	}
	
	@Test
	void testCloneConstructor() {
		Position3D pos1 = new Position3D(1,1,1);
		Position3D pos2 = new Position3D(pos1);
		Position3D expected = new Position3D(1,1,1);
		assertEquals(expected, pos2);
	}

}
