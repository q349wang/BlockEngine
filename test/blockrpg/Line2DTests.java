package blockrpg;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class Line2DTests {

	@Test
	void testCloneConstructor() {
		Line2D expected = new Line2D();
		double dirCoords[] = { 2.0/3, 1.0/3};
		double posCoords[] = { 1, 2 };
		expected.setDir(dirCoords);
		expected.setPos(posCoords);
		Line2D test = new Line2D(expected);
		assertEquals(expected, test);
	}

	@Test
	void testCloneFunction() {
		Line2D expected = new Line2D();
		double dirCoords[] = { 2.0/3, 1.0/3 };
		double posCoords[] = { 1, 2 };
		expected.setDir(dirCoords);
		expected.setPos(posCoords);
		Line2D test = expected.clone();
		
		assertEquals(expected, test);
		assertFalse(expected == test);
		assertFalse(expected.getDir() == test.getDir());
		assertFalse(expected.getPos() == test.getPos());
	}

	@Test
	void testSetAndGetDir() {
		Line2D test = new Line2D();
		double dirCoords[] = { 1, 2};
		Vector2D expected = new Vector2D(1, 2);
		test.setDir(dirCoords);
		assertEquals(expected.normalize(), test.getDir());
	}

	@Test
	void testSetAndGetPos() {
		Line2D test = new Line2D();
		double posCoords[] = { 1, 2 };
		Position2D expected = new Position2D(1, 2);
		test.setPos(posCoords);
		assertEquals(expected, test.getPos());
	}

	@Test
	void testGetLinePoint() {
		Line2D test = new Line2D();
		double dirCoords[] = { 3.0/5, 4.0/5};
		double posCoords[] = { 1.0, 2.0 };
		test.setDir(dirCoords);
		test.setPos(posCoords);
		Position2D expected = new Position2D(-1.0/5,2.0/5);
		assertEquals(expected, test.getLinePoint(-2.0));
	}

	@Test
	void testRotateDir() {
		Line2D test = new Line2D();
		double dirCoords[] = {0, 2};
		test.setDir(dirCoords);
		Vector2D expected = new Vector2D(0, -2);
		test.rotateDir(Math.PI);
		assertEquals(expected.normalize(), test.getDir());
	}

	@Test
	void testRotatePos() {
		Line2D test = new Line2D();
		double dirCoords[] = {0, 2};
		double posCoords[] = {0,3};
		test.setDir(dirCoords);
		test.setPos(posCoords);
		Vector2D expectedVec = new Vector2D(0,-2);
		Position2D expectedPos = new Position2D(0,-3);
		test.rotatePos(Math.PI);
		assertEquals(expectedVec.normalize(), test.getDir());
		assertEquals(expectedPos, test.getPos());
	}

	@Test
	void testIsParallel() {
		Line2D test1 = new Line2D();
		Line2D test2 = new Line2D();
		double dirCoords1[] = {1, 2};
		double dirCoords2[] = {-2,-4};
		test1.setDir(dirCoords1);
		test2.setDir(dirCoords2);
		assertTrue(test1.isParallel(test2));
		assertTrue(test2.isParallel(test1));
	}

	@Test
	void testIntersectsTrue() {
		Line2D test1 = new Line2D();
		Line2D test2 = new Line2D();
		double dirCoords1[] = {1, 2};
		double dirCoords2[] = {3,4};
		
		double posCoords1[] = {1,1};
		double posCoords2[] = {0,-2};
		
		test1.setDir(dirCoords1);
		test2.setDir(dirCoords2);
		test1.setPos(posCoords1);
		test2.setPos(posCoords2);

		Position2D expected = new Position2D(-1.5,-4);
		assertEquals(expected, test1.intersects(test2));
		assertEquals(expected, test2.intersects(test1));
	}
	
	@Test
	void testIntersectsFalse() {
		Line2D test1 = new Line2D();
		Line2D test2 = new Line2D();
		double dirCoords1[] = {1, 2};
		double dirCoords2[] = {-1,-2};
		
		double posCoords1[] = {1,1};
		double posCoords2[] = {2,1};
		
		test1.setDir(dirCoords1);
		test2.setDir(dirCoords2);
		test1.setPos(posCoords1);
		test2.setPos(posCoords2);

		assertEquals(null, test1.intersects(test2));
		assertEquals(null, test2.intersects(test1));
	}

	
	@Test
	void testOnLineTrue() {
		Line2D test1 = new Line2D();
		double dirCoords1[] = {1, 2};
		
		double posCoords1[] = {1,1};
		
		Position2D point = new Position2D(2, 3);
		
		test1.setDir(dirCoords1);
		test1.setPos(posCoords1);

		assertTrue(test1.onLine(point));
	}
	
	@Test
	void testOnLineFalse() {
		Line2D test1 = new Line2D();
		double dirCoords1[] = {1, 2};
		
		double posCoords1[] = {1,1};
		
		Position2D point = new Position2D(2, 4);
		
		test1.setDir(dirCoords1);
		test1.setPos(posCoords1);

		assertFalse(test1.onLine(point));
	}
	
	@Test
	void testSimilarTrue() {
		Line2D test1 = new Line2D();
		Line2D test2 = new Line2D();
		double dirCoords1[] = {1, 2};
		double dirCoords2[] = {-2,-4};
		
		double posCoords1[] = {1,1};
		double posCoords2[] = {2,3};
		
		test1.setDir(dirCoords1);
		test2.setDir(dirCoords2);
		test1.setPos(posCoords1);
		test2.setPos(posCoords2);

		assertTrue(test1.similar(test2));
		assertTrue(test2.similar(test1));
	}
	
	@Test
	void testSimilarFalseLine() {
		Line2D test1 = new Line2D();
		Line2D test2 = new Line2D();
		double dirCoords1[] = {1, 2};
		double dirCoords2[] = {-2,-3};
		
		double posCoords1[] = {1,1};
		double posCoords2[] = {2,3};
		
		test1.setDir(dirCoords1);
		test2.setDir(dirCoords2);
		test1.setPos(posCoords1);
		test2.setPos(posCoords2);

		assertFalse(test1.similar(test2));
		assertFalse(test2.similar(test1));
	}
	
	@Test
	void testSimilarFalsePoint() {
		Line2D test1 = new Line2D();
		Line2D test2 = new Line2D();
		double dirCoords1[] = {1, 2};
		double dirCoords2[] = {-2,-4};
		
		double posCoords1[] = {1,1};
		double posCoords2[] = {2,4};
		
		test1.setDir(dirCoords1);
		test2.setDir(dirCoords2);
		test1.setPos(posCoords1);
		test2.setPos(posCoords2);

		assertFalse(test1.similar(test2));
		assertFalse(test2.similar(test1));
	}

}
