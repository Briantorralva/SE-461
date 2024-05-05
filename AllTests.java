package org.psnbtech;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({ BoardPanelTest.class, ClockTest.class, DirectionTest.class, SidePanelTest.class, SnakeGameTest.class,
		TileTypeTest.class })
public class AllTests {

}
