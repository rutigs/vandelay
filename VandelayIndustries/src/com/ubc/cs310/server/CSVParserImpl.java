package com.ubc.cs310.server;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import com.ubc.cs310.client.CSVParser;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class CSVParserImpl extends RemoteServiceServlet implements
		CSVParser {

	public String CSVParse(String input) throws IllegalArgumentException {

		String serverInfo = getServletContext().getServerInfo();
		String userAgent = getThreadLocalRequest().getHeader("User-Agent");

		// Escape data from the client to avoid cross-site script vulnerabilities.
		input = escapeHtml(input);
		userAgent = escapeHtml(userAgent);

		return "Hello, " + input + "!<br><br>I am running " + serverInfo
				+ ".<br><br>It looks like you are using:<br>" + userAgent;
	}

	/**
	 * Escape an html string. Escaping data received from the client helps to
	 * prevent cross-site script vulnerabilities.
	 * 
	 * @param html the html string to escape
	 * @return the escaped string
	 */
	private String escapeHtml(String html) {
		if (html == null) {
			return null;
		}
		return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;")
				.replaceAll(">", "&gt;");
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
				String name = new String();
				String urlString = new String();
				String type = new String();
				String primaryUse = new String();
				String address = new String();
				String localArea = new String();
				String ownership = new String();
				
				// Split attributes by commas.
				String[] attributes = line.split(splitBy);

				for(int i = 0; i<8;i++){
					switch(i)
					{
					case 0:
						if(attributes[0] == ""){
							name = "N/A";
							break;
						}
						else
							name = attributes[0];
							break;
					case 1:
						if(attributes[1] == ""){
							urlString = "N/A";
							break;
						}
						else
							urlString = attributes[1];
							break;
					case 2:
						if(attributes[2] == ""){
							type = "N/A";
							break;
						}
						else
							type = attributes[2];
							break;
					case 3:
						if(attributes[3] == ""){
							primaryUse = "N/A";
							break;
						}
						else
							primaryUse = attributes[3];
					case 4:
						if(attributes[4] == ""){
							address = "N/A";
							break;
						}
						else 
							address = attributes[4];
							break;
					case 5:
						if(attributes[5] == ""){
							localArea = "N/A";
							break;
						}
						else
							localArea = attributes[5];
							break;
					case 6:
						if(attributes[6] == ""){
							ownership = "N/A";
							break;
						}
						else
							ownership = attributes[6];
							break;
					}
				}
				
				// To be implemented in second sprint.
				/*
				 * float lon = Float.parseFloat(attributes[10]); float lat =
				 * Float.parseFloat(attributes[11]);
				 * space.setLocation(attributes[4], lat, lon);
				 */
				Space space = new Space (name, urlString, type, primaryUse, address, localArea, ownership);
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
		spaces.remove(0);
	}	
}
