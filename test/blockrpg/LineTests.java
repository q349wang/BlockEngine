package blockrpg;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class LineTests {

	@Test
	void testCloneConstructor() {
		Line expected = new Line();
		double dirCoords[] = { 2.0/3, 1.0/3, 2.0/3 };
		double posCoords[] = { 1, 2, 3 };
		expected.setDir(dirCoords);
		expected.setPos(posCoords);
		Line test = new Line(expected);
		assertEquals(expected, test);
	}
	
	@Test
	void testCloneFunction() {
		Line expected = new Line();
		double dirCoords[] = { 2.0/3, 1.0/3, 2.0/3 };
		double posCoords[] = { 1, 2, 3 };
		expected.setDir(dirCoords);
		expected.setPos(posCoords);
		Line test = expected.clone();
		
		assertEquals(expected, test);
		assertFalse(expected == test);
		assertFalse(expected.getDir() == test.getDir());
		assertFalse(expected.getPos() == test.getPos());
	}
	
	@Test
	void testSetAndGetDir() {
		Line test = new Line();
		double dirCoords[] = { 1, 2, 3 };
		Vector3D expected = new Vector3D(1, 2, 3);
		test.setDir(dirCoords);
		assertEquals(expected.normalize(), test.getDir());
	}

	@Test
	void testSetAndGetPos() {
		Line test = new Line();
		double posCoords[] = { 1, 2, 3 };
		Position3D expected = new Position3D(1, 2, 3);
		test.setPos(posCoords);
		assertEquals(expected, test.getPos());
	}

	@Test
	void testGetLinePoint() {
		Line test = new Line();
		double dirCoords[] = { 2.0/3, 1.0/3, 2.0/3 };
		double posCoords[] = { 1, 2, 3 };
		test.setDir(dirCoords);
		test.setPos(posCoords);
		Position3D expected = new Position3D(-1.0/3,4.0/3,5.0/3);
		assertEquals(expected, test.getLinePoint(-2.0));
	}

	@Test
	void testRotateDir() {
		Line test = new Line();
		double dirCoords[] = {0, 2, 0};
		test.setDir(dirCoords);
		Vector3D axis = new Vector3D(1, 1, 0);
		Vector3D expected = new Vector3D(2, 0, 0);
		assertEquals(expected.normalize(), test.rotateDir(Math.PI, axis).getDir());
	}

	@Test
	void testRotatePos() {
		Line test = new Line();
		double dirCoords[] = {0, 2, 0};
		double posCoords[] = {0,3,0};
		test.setDir(dirCoords);
		test.setPos(posCoords);
		Vector3D axis = new Vector3D(1, 1, 0);
		Vector3D expectedVec = new Vector3D(2, 0, 0);
		Position3D expectedPos = new Position3D(3,0,0);
		assertEquals(expectedVec.normalize(), test.rotateDir(Math.PI, axis).getDir());
		assertEquals(expectedPos, test.rotatePos(Math.PI, axis).getPos());
	}

	@Test
	void testIsParallel() {
		Line test1 = new Line();
		Line test2 = new Line();
		double dirCoords1[] = {1, 2, 4};
		double dirCoords2[] = {-2,-4,-8};
		test1.setDir(dirCoords1);
		test2.setDir(dirCoords2);
		assertTrue(test1.isParallel(test2));
		assertTrue(test2.isParallel(test1));
	}

	@Test
	void testIntersectsParallel() {
		Line test1 = new Line();
		Line test2 = new Line();
		double dirCoords1[] = {1, 2, 4};
		double dirCoords2[] = {-2,-4,-8};
		
		test1.setDir(dirCoords1);
		test2.setDir(dirCoords2);
		assertEquals(null,test1.intersects(test2));
		assertEquals(null,test2.intersects(test1));
	}
	
	@Test
	void testIntersectsTypical() {
		Line test1 = new Line();
		Line test2 = new Line();
		double dirCoords1[] = {1, 2, 3};
		double dirCoords2[] = {-1,5,-7};
		
		double posCoords1[] = {1,1,0};
		double posCoords2[] = {2,3,3};
		
		test1.setDir(dirCoords1);
		test2.setDir(dirCoords2);
		test1.setPos(posCoords1);
		test2.setPos(posCoords2);
		
		Position3D expected = new Position3D(2,3,3);
		assertEquals(expected, test1.intersects(test2));
		assertEquals(expected, test2.intersects(test1));
	}

	@Test
	void testIntersectsNotIntersectsNotParallel() {
		Line test1 = new Line();
		Line test2 = new Line();
		double dirCoords1[] = {1, 2, 3};
		double dirCoords2[] = {-1,5,-7};
		
		double posCoords1[] = {1,1,0};
		double posCoords2[] = {2,1,0};
		
		test1.setDir(dirCoords1);
		test2.setDir(dirCoords2);
		test1.setPos(posCoords1);
		test2.setPos(posCoords2);

		assertEquals(null, test1.intersects(test2));
		assertEquals(null, test2.intersects(test1));
	}
	
	@Test
	void testOnLineTrue() {
		Line test1 = new Line();
		double dirCoords1[] = {1, 2, 3};
		
		double posCoords1[] = {1,1,0};
		
		Position3D point = new Position3D(2, 3, 3);
		
		test1.setDir(dirCoords1);
		test1.setPos(posCoords1);

		assertTrue(test1.onLine(point));
	}
	
	@Test
	void testOnLineFalse() {
		Line test1 = new Line();
		double dirCoords1[] = {1, 2, 3};
		
		double posCoords1[] = {1,1,0};
		
		Position3D point = new Position3D(2, 7, 3);
		
		test1.setDir(dirCoords1);
		test1.setPos(posCoords1);

		assertFalse(test1.onLine(point));
	}
	
	@Test
	void testSimilarTrue() {
		Line test1 = new Line();
		Line test2 = new Line();
		double dirCoords1[] = {1, 2, 3};
		double dirCoords2[] = {-2,-4,-6};
		
		double posCoords1[] = {1,1,0};
		double posCoords2[] = {2,3,3};
		
		test1.setDir(dirCoords1);
		test2.setDir(dirCoords2);
		test1.setPos(posCoords1);
		test2.setPos(posCoords2);

		assertTrue(test1.similar(test2));
		assertTrue(test2.similar(test1));
	}
	
	@Test
	void testSimilarFalseLine() {
		Line test1 = new Line();
		Line test2 = new Line();
		double dirCoords1[] = {1, 1, 3};
		double dirCoords2[] = {-2,-4,-6};
		
		double posCoords1[] = {1,1,0};
		double posCoords2[] = {2,3,3};
		
		test1.setDir(dirCoords1);
		test2.setDir(dirCoords2);
		test1.setPos(posCoords1);
		test2.setPos(posCoords2);

		assertFalse(test1.similar(test2));
		assertFalse(test2.similar(test1));
	}
	
	@Test
	void testSimilarFalsePoint() {
		Line test1 = new Line();
		Line test2 = new Line();
		double dirCoords1[] = {1, 2, 3};
		double dirCoords2[] = {-2,-4,-6};
		
		double posCoords1[] = {1,1,8};
		double posCoords2[] = {2,3,3};
		
		test1.setDir(dirCoords1);
		test2.setDir(dirCoords2);
		test1.setPos(posCoords1);
		test2.setPos(posCoords2);

		assertFalse(test1.similar(test2));
		assertFalse(test2.similar(test1));
	}
}
