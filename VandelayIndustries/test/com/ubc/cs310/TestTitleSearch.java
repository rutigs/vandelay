package com.ubc.cs310;

import java.util.ArrayList;
import java.util.List;
<<<<<<< HEAD

import org.junit.Test;

=======
import org.junit.Test;
import static org.junit.Assert.*;
>>>>>>> f492d8d8f01e6c375b537fca915e9cf16ef3b934
import com.ubc.cs310.server.SpaceServiceImpl;
import com.ubc.cs310.server.Space;

public class TestTitleSearch {

	// Create Spaces here
	Space space1 = new Space("English Word", "", "", "", "", "", "");
	//space1.setName("English word");
	Space space2 = new Space("Old English", "", "", "", "", "", "");
	//space2.setName("Old English");
	Space space3 = new Space("New Language", "", "", "", "", "", "");
	//space3.setName("New Language");

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
