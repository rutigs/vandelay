package com.ubc.cs310;

import java.util.ArrayList;
import java.util.List;
import com.ubc.cs310.server.SpaceServiceImpl;

public class TestTitleSearch {

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
	private void testEmptyTitleSearch() {
		List<Space> emptyTitleList = new ArrayList<Space>();
		assertEquals(emptyTitleList, SpaceServiceImpl.titleSearch(noMatches, emptyTitleList));
	}
	
	// Test empty string as parameter expect full list returned
	@Test
	private void testEmptyParamTitleSearch() {
		List<Space> titleList = new ArrayList<Space>();
		titleList.add(space1);
		assertEquals(titleList, SpaceServiceImpl.titleSearch("", titleList));
	}
	
	@Test
	private void testOneTitleSearch() {
		List<Space> titleList = new ArrayList<Space>();
		titleList.add(space1);
		titleList.add(space2);
		titleList.add(space3);
		List<Space> resultList = new ArrayList<Space>();
		resultList.add(space1);
		assertEquals(resultList, SpaceServiceImpl.titleSearch(param1, titleList));
	}
	
	@Test
	private void testTwoTitleSearch() {
		List<Space> titleList = new ArrayList<Space>();
		titleList.add(space1);
		titleList.add(space2);
		titleList.add(space3);
		List<Space> resultList = new ArrayList<Space>();
		resultList.add(space1);
		resultList.add(space2);
		assertEquals(resultList, SpaceServiceImpl.titleSearch(param2, titleList));
	}
	
	@Test
	private void testInvalidTitleSearch() {
		List<Space> titleList = new ArrayList<Space>();
		titleList.add(space1);
		List<Space> resultList = new ArrayList<Space>();
		assertEquals(resultList, SpaceServiceImpl.titleSearch(noMatches, titleList));
	}
}
