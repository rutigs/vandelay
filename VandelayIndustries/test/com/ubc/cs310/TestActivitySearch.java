package com.ubc.cs310;

import java.util.ArrayList;
import java.util.List;
import com.ubc.cs310.server.SpaceServiceImpl;

public class TestActivitySearch {

	// Create Spaces here
	Space space1 = new Space();
	Space space2 = new Space();
	Space space3 = new Space();
	
	// Create parameters here
	String noMatches = "Card";
	String param1 = "";
	String param2 = "";
	
	// Test empty list of Spaces (this shouldn't happen in reality)
	@Test
	private void testEmptyActivitySearch() {
		List<Space> emptyActivityList = new ArrayList<Space>();
		assertEquals(emptyActivityList, SpaceServiceImpl.activitySearch(noMatches, emptyActivityList));
	}
	
	// Test empty string as parameter expect full list returned
	@Test
	private void testEmptyParamActivitySearch() {
		List<Space> activityList = new ArrayList<Space>();
		activityList.add(space1);
		assertEquals(activityList, SpaceServiceImpl.activitySearch("", activityList));
	}
	
	@Test
	private void testOneActivitySearch() {
		List<Space> activityList = new ArrayList<Space>();
		activityList.add(space1);
		activityList.add(space2);
		activityList.add(space3);
		List<Space> resultList = new ArrayList<Space>();
		resultList.add(space1);
		assertEquals(resultList, SpaceServiceImpl.activitySearch(param1, activityList));
	}
	
	@Test
	private void testTwoActivitySearch() {
		List<Space> activityList = new ArrayList<Space>();
		activityList.add(space1);
		activityList.add(space2);
		activityList.add(space3);
		List<Space> resultList = new ArrayList<Space>();
		resultList.add(space1);
		resultList.add(space2);
		assertEquals(resultList, SpaceServiceImpl.activitySearch(param2, activityList));
	}
	
	@Test
	private void testInvalidActivitySearch() {
		List<Space> activityList = new ArrayList<Space>();
		activityList.add(space1);
		List<Space> resultList = new ArrayList<Space>();
		assertEquals(resultList, SpaceServiceImpl.activitySearch(noMatches, activityList));
	}
}

