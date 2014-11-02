package com.ubc.cs310.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface CSVParserAsync {
	void CSVParse(String input, AsyncCallback<String> callback)
			throws IllegalArgumentException;
}
