package com.ubc.cs310;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.ubc.cs310.server.SpaceServiceImpl;
import com.ubc.cs310.server.Space;

public class TestTitleSearch {

	// Create Spaces here
	Space space1 = new Space("word", "", "", "", "", "", "");
	Space space2 = new Space();
	space2.setName("Old English");
	Space space3 = new Space();
	space3.setName("New Language");
	
	// Create parameters here
	String noMatches = "Card";
	String param1 = "word";
	String param2 = "English";
	
	// Test empty list of Spaces (this shouldn't happen in reality)
	@Test
	public void testEmptyTitleSearch() {
		ArrayList<Space> emptyTitleList = new ArrayList<Space>();
		assertArrayEquals(emptyTitleList, SpaceServiceImpl.titleSearch(noMatches, emptyTitleList));
	}
	
	// Test empty string as parameter expect full list returned
	@Test
	public void testEmptyParamTitleSearch() {
		ArrayList<Space> titleList = new ArrayList<Space>();
		titleList.add(space1);
		assertArrayEquals(titleList, SpaceServiceImpl.titleSearch("", titleList));
	}
	
	@Test
	public void testOneTitleSearch() {
		ArrayList<Space> titleList = new ArrayList<Space>();
		titleList.add(space1);
		titleList.add(space2);
		titleList.add(space3);
		ArrayList<Space> resultList = new ArrayList<Space>();
		resultList.add(space1);
		assertArrayEquals(resultList, SpaceServiceImpl.titleSearch(param1, titleList));
	}
	
	@Test
	public void testTwoTitleSearch() {
		ArrayList<Space> titleList = new ArrayList<Space>();
		titleList.add(space1);
		titleList.add(space2);
		titleList.add(space3);
		ArrayList<Space> resultList = new ArrayList<Space>();
		resultList.add(space1);
		resultList.add(space2);
		assertArrayEquals(resultList, SpaceServiceImpl.titleSearch(param2, titleList));
	}
	
	@Test
	public void testInvalidTitleSearch() {
		ArrayList<Space> titleList = new ArrayList<Space>();
		titleList.add(space1);
		ArrayList<Space> resultList = new ArrayList<Space>();
		assertArrayEquals(resultList, SpaceServiceImpl.titleSearch(noMatches, titleList));
	}
}
