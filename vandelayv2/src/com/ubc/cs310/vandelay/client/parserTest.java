package com.ubc.cs310.vandelay.client;


import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import com.ubc.cs310.vandelay.shared.Space;

public class parserTest {
	static int LAST = 353;
	static int FIRST = 0;
	private ArrayList<Space> testSpaces;
	Space first = new Space(
			"15th Field Artillery Regiment Museum and Archives",
			"www.memorybc.ca/museum-of-15th-field-artillery-regiment",
			"Museum/Gallery", "Museum/Gallery",
			"2025 W 11th Av,  Vancouver,  BC,  V6J 2C7", 
			"Kitsilano",
			"Privately Owned", 
			49.261938, -123.151123);
	Space last = new Space(
			"Yuk Yuk's Comedy Club",
			"http://www.yukyuks.com",
			"Theatre/Performance", 
			"Performance Space",
			"2837 Cambie St,  Vancouver,  BC,  V5Z 3Y8", 
			"Fairview",
			"Privately Owned", 
			49.2600654, -123.1151069);
						

	
	public ArrayList<Space> parse(String url) {
		BufferedReader br = null;
		ArrayList<Space> spaces = new ArrayList<Space>();
		try {

			URL url2 = new URL(url);
			URLConnection urlConnection = url2.openConnection();
			br = new BufferedReader(new InputStreamReader(
					urlConnection.getInputStream()));
			String line = "";
			String splitBy = ",";
			while ((line = br.readLine()) != null) {
				String name = new String();
				String urlString = new String();
				String type = new String();
				String primaryUse = new String();
				String address = new String();
				String localArea = new String();
				String ownership = new String();
				double lat = 0;
				double lon = 0;

				// Split attributes by commas.
				String[] attributes = line.split(splitBy);

				for (int i = 0; i < 8; i++) {
					switch (i) {
					case 0:

						if (attributes[0] == "") {
							name = "N/A";
						} else {
							name = attributes[0];
							// LOG.log(Level.INFO, "name" + name);
						}
						break;
					case 1:
						if (attributes[1] == "") {
							urlString = "N/A";
						} else
							urlString = attributes[1];
						break;
					case 2:
						if (attributes[2] == "") {
							type = "N/A";
						} else
							type = attributes[2];
						break;
					case 3:
						if (attributes[3] == "") {
							primaryUse = "N/A";
						} else
							primaryUse = attributes[3];
						break;
					case 4:
						if (attributes[4] == "") {
							address = "N/A";
						} else {
							String street = attributes[4];
							street = street.substring(1, street.length());
							String city = attributes[5];
							String prov = attributes[6];
							String postalCode = attributes[7];
							postalCode = postalCode.substring(0,
									postalCode.length() - 1);

							address = street + ", " + city + ", " + prov + ", "
									+ postalCode;
						}
						break;
					case 5:
						if (attributes[8] == "") {
							localArea = "N/A";
						} else
							localArea = attributes[8];
						break;
					case 6:
						if (attributes[9] == "") {
							ownership = "N/A";
						} else
							ownership = attributes[9];
						break;
					case 7:
						lon = Double.parseDouble(attributes[13]);
						lat = Double.parseDouble(attributes[14]);
						break;
					}
				}
				Space space = new Space(name, urlString, type, primaryUse,
						address, localArea, ownership, lat, lon);
				spaces.add(space);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
		}
		return spaces;
	}

	@Before
	public void setUp() {
		testSpaces = parse("http://m.uploadedit.com/b042/1416876073660.txt");
		//LAST = (testSpaces.size() - 1);
	}
	
	@Test
	public void testNameFirst() {
		assertEquals(first.getName(), testSpaces.get(FIRST).getName());
	}

	@Test
	public void testURLFirst() {
		assertEquals(first.getURL(), testSpaces.get(FIRST).getURL());
	}

	@Test
	public void testTypeFirst() {
		assertEquals(first.getType(), testSpaces.get(FIRST).getType());
	}

	@Test
	public void testPrimaryUseFirst() {
		assertEquals(first.getPrimaryUse(), testSpaces.get(FIRST).getPrimaryUse());
	}

	@Test
	public void testAddressFirst() {
		assertEquals(first.getAddress(), testSpaces.get(FIRST).getAddress());
	}

	@Test
	public void testLocalAreaFirst() {
		assertEquals(first.getLocalArea(), testSpaces.get(FIRST).getLocalArea());
	}

	@Test
	public void testOwnershipFirst() {
		assertEquals(first.getOwnerShip(), testSpaces.get(FIRST).getOwnerShip());
	}

	@Test
	public void testLatFirst() {
		assertEquals(first.getLat(), testSpaces.get(FIRST).getLat(), 0.0);
	}

	@Test
	public void testLonFirst() {
		assertEquals(first.getLon(), testSpaces.get(FIRST).getLon(), 0.0);
	}

	@Test
	public void testNameLast() {
		assertEquals(last.getName(), testSpaces.get(LAST).getName());
	}

	@Test
	public void testURLLast() {
		assertEquals(last.getURL(), testSpaces.get(LAST).getURL());
	}

	@Test
	public void testTypeLast() {
		assertEquals(last.getType(), testSpaces.get(LAST).getType());
	}

	@Test
	public void testPrimaryUseLast() {
		assertEquals(last.getPrimaryUse(), testSpaces.get(LAST).getPrimaryUse());
	}

	@Test
	public void testAddressLast() {
		assertEquals(last.getAddress(), testSpaces.get(LAST).getAddress());
	}

	@Test
	public void testLocalAreaLast() {
		assertEquals(last.getLocalArea(), testSpaces.get(LAST).getLocalArea());
	}

	@Test
	public void testOwnershipLast() {
		assertEquals(last.getOwnerShip(), testSpaces.get(LAST).getOwnerShip());
	}

	@Test
	public void testLatLast() {
		assertEquals(last.getLat(), testSpaces.get(LAST).getLat(), 0.0);
	}

	@Test
	public void testLonLast() {
		assertEquals(last.getLon(), testSpaces.get(LAST).getLon(), 0.0);
	}
}
