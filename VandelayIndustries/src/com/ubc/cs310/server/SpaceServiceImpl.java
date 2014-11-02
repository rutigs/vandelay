package com.ubc.cs310.server;

import java.util.ArrayList;
import java.util.List;

import com.ubc.cs310.client.GreetingService;
import com.ubc.cs310.shared.FieldVerifier;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class SpaceServiceImpl extends RemoteServiceServlet implements
		GreetingService {

	public String greetServer(String input) throws IllegalArgumentException {
		// Verify that the input is valid. 
		if (!FieldVerifier.isValidName(input)) {
			// If the input is not valid, throw an IllegalArgumentException back to
			// the client.
			throw new IllegalArgumentException(
					"Name must be at least 4 characters long");
		}

		String serverInfo = getServletContext().getServerInfo();
		String userAgent = getThreadLocalRequest().getHeader("User-Agent");

		// Escape data from the client to avoid cross-site script vulnerabilities.
		input = escapeHtml(input);
		userAgent = escapeHtml(userAgent);

		return "Hello, " + input + "!<br><br>I am running " + serverInfo
				+ ".<br><br>It looks like you are using:<br>" + userAgent;
	}
	
	public static ArrayList<Space> titleSearch(String parameter, ArrayList<Space> originalList) { //create Space class
		// create empty list of search results
		ArrayList<Space> titleList = new ArrayList<Space>();
		// loop through elements of oriinalList
		for(Space space : originalList) {
			// use regex *parameter* to test equivalence
			String title = space.getName(); //code getActivity
			if(title.matches("*" + parameter + "*")) {
				titleList.add(space);
			}
		} // case of empty string for parameter should be covered by for loop
		return titleList;
	}
	
	public static ArrayList<Space> activitySearch(String parameter, ArrayList<Space> originalList) { //create Space class
		// create empty list of search results
		ArrayList<Space> activityList = new ArrayList<Space>();
		// loop through elements of oriinalList
		for(Space space : originalList) {
			// use regex *parameter* to test equivalence
			String activity = space.getType();
			if(activity.matches("*" + parameter + "*")) {
				activityList.add(space);
			}
		} // case of empty string for parameter should be covered by for loop
		return activityList;
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
}
