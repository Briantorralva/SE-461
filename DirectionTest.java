package org.psnbtech;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DirectionTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testEnumValues() {
		assertEquals(Direction.North, Direction.valueOf("North"));
		assertEquals(Direction.South, Direction.valueOf("South"));
		assertEquals(Direction.East, Direction.valueOf("East"));
		assertEquals(Direction.West, Direction.valueOf("West"));
	}

}
