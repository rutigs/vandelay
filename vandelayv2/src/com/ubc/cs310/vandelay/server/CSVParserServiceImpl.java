package com.ubc.cs310.vandelay.server;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
//import java.net.URL;
import java.util.ArrayList;
//import java.util.List;

import com.ubc.cs310.vandelay.client.CSVParserService;
import com.ubc.cs310.vandelay.shared.Space;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class CSVParserServiceImpl extends RemoteServiceServlet implements
		CSVParserService {
	
//	private static final Logger LOG = LogCSVParserServiceImpl(CSVParserImpl.class.getName());

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
	
	public ArrayList<Space> parse() {
		BufferedReader br = null;
		ArrayList<Space> spaces = new ArrayList<Space>();

		try {

			InputStream iStream = this.getClass().getClassLoader().getResourceAsStream("CulturalSpaces.csv");
			// Create a reader for the input stream.
			br = new BufferedReader(new InputStreamReader(iStream));
			
			//	Testing log for BufferedReader
//			StringBuilder out = new StringBuilder();
//			String line2;
//			while ((line2 = br.readLine()) != null) {
//				out.append(line2);
//			}
//			LOG.log(Level.INFO, out.toString());

			String line = "";
			String splitBy = ",";
			while ((line = br.readLine()) != null) 
			{
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

				for(int i = 0; i<8;i++){
					switch(i)
					{
					case 0:
						
						if(attributes[0] == ""){
							name = "N/A";
						}
						else {
							name = attributes[0];
							//LOG.log(Level.INFO, "name" + name);
						}
						break;
					case 1:
						if(attributes[1] == ""){
							urlString = "N/A";
						}
						else
							urlString = attributes[1];
						break;
					case 2:
						if(attributes[2] == ""){
							type = "N/A";
						}
						else
							type = attributes[2];
						break;
					case 3:
						if(attributes[3] == ""){
							primaryUse = "N/A";
						}
						else
							primaryUse = attributes[3];
						break;
					case 4:
						if(attributes[4] == ""){
							address = "N/A";
						}
						else {
							String street = attributes[4];
							street = street.substring(1, street.length());
							String city = attributes[5];
							String prov = attributes[6];
							String postalCode = attributes[7];
							postalCode = postalCode.substring(0, postalCode.length()-1);
								
							address = street + ", " + city + ", " + prov + ", " + postalCode;
						}
						break;
					case 5:
						if(attributes[8] == ""){
							localArea = "N/A";
						}
						else
							localArea = attributes[8];
						break;
					case 6:
						if(attributes[9] == ""){
							ownership = "N/A";
						}
						else
							ownership = attributes[9];
						break;
					case 7:
//							if(attributes[11] == "LATITUDE")
//								lat = 0.0;
//							if(attributes[12] == "LONGITUDE")
//								lon = 0.0;
							lon = Double.parseDouble(attributes[13]);
							lat = Double.parseDouble(attributes[14]);
						break;
					}
				}
				
				// To be implemented in second sprint.
				/*
				 * float lon = Float.parseFloat(attributes[10]); float lat =
				 * Float.parseFloat(attributes[11]);
				 * space.setLocation(attributes[4], lat, lon);
				 */
				//LOG.log(Level.INFO, "name, urlString, type, primaryUse, address, localArea, ownership"
				//			+ name + urlString + type + primaryUse + address + localArea + ownership);
				
				Space space = new Space (name, urlString, type, primaryUse, address, localArea, ownership, lat, lon);
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
//		if(spaces.size() > 0)
//			spaces.remove(0);
		return (ArrayList<Space>) spaces;
	}	
}