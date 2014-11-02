package com.ubc.cs310.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface SpaceServiceAsync {
	void spaceServer(String input, AsyncCallback<String> callback)
			throws IllegalArgumentException;
}
