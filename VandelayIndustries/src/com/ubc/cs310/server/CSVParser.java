package com.ubc.cs310.server;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class CSVParser {

	public static void main(String[] args) {

		CSVParser file = new CSVParser();
		file.parse();
	}

	public void parse() {
		BufferedReader br = null;
		List<Space> spaces = new ArrayList<Space>();

		try {
			// Create the file URL from URL string.
			URL url = new URL(
					"ftp://webftp.vancouver.ca/opendata/CulturalSpace/CulturalSpaces.csv");
			// Create an input stream from the URL file being read.
			InputStream iStream = url.openStream();
			// Create a reader for the input stream.
			br = new BufferedReader(new InputStreamReader(iStream));

			// List<Space> spaces = new ArrayList<Space>();
			String line = "";
			String splitBy = ",";
			while ((line = br.readLine()) != null) {

				// Split attributes by commas.
				String[] attributes = line.split(splitBy);

				Space space = new Space();

				// Set attributes for the space.
				space.setName(attributes[0]);
				space.setURL(attributes[1]);
				space.setType(attributes[2]);
				space.setPrimaryUse(attributes[3]);
				space.setAddress(attributes[4]);
				space.setLocalArea(attributes[5]);
				space.setOwnership(attributes[6]);

				// To be implemented in second sprint.
				/*
				 * float lon = Float.parseFloat(attributes[10]); float lat =
				 * Float.parseFloat(attributes[11]);
				 * space.setLocation(attributes[4], lat, lon);
				 */

				spaces.add(space);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		;
		spaces.remove(0);
	}

}
