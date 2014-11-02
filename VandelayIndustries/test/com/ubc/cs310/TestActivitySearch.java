package com.ubc.cs310;

import java.util.ArrayList;
import java.util.List;
import com.ubc.cs310.server.SpaceServiceImpl;
import com.ubc.cs310.server.Space;

public class TestActivitySearch {

	// Create Spaces here
	Space space1 = new Space();
	space1.setType("English word");
	Space space2 = new Space();
	space2.setType("Old English");
	Space space3 = new Space();
	space3.setType("New Language");
	
	// Create parameters here
	String noMatches = "Card";
	String param1 = "word";
	String param2 = "English";
	
	// Test empty list of Spaces (this shouldn't happen in reality)
	@Test
	public void testEmptyActivitySearch() {
		ArrayList<Space> emptyActivityList = new ArrayList<Space>();
		assertArrayEquals(emptyActivityList, SpaceServiceImpl.activitySearch(noMatches, emptyActivityList));
	}
	
	// Test empty string as parameter expect full list returned
	@Test
	public void testEmptyParamActivitySearch() {
		ArrayList<Space> activityList = new ArrayList<Space>();
		activityList.add(space1);
		assertArrayEquals(activityList, SpaceServiceImpl.activitySearch("", activityList));
	}
	
	@Test
	public void testOneActivitySearch() {
		ArrayList<Space> activityList = new ArrayList<Space>();
		activityList.add(space1);
		activityList.add(space2);
		activityList.add(space3);
		ArrayList<Space> resultList = new ArrayList<Space>();
		resultList.add(space1);
		assertArrayEquals(resultList, SpaceServiceImpl.activitySearch(param1, activityList));
	}
	
	@Test
	public void testTwoActivitySearch() {
		ArrayList<Space> activityList = new ArrayList<Space>();
		activityList.add(space1);
		activityList.add(space2);
		activityList.add(space3);
		ArrayList<Space> resultList = new ArrayList<Space>();
		resultList.add(space1);
		resultList.add(space2);
		assertArrayEquals(resultList, SpaceServiceImpl.activitySearch(param2, activityList));
	}
	
	@Test
	public void testInvalidActivitySearch() {
		ArrayList<Space> activityList = new ArrayList<Space>();
		activityList.add(space1);
		ArrayList<Space> resultList = new ArrayList<Space>();
		assertArrayEquals(resultList, SpaceServiceImpl.activitySearch(noMatches, activityList));
	}
}

