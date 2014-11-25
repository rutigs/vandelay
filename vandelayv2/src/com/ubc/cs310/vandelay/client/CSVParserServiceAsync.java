package com.ubc.cs310.vandelay.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.ubc.cs310.vandelay.shared.Space;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface CSVParserServiceAsync {
	void CSVParse(String input, AsyncCallback<String> callback)
			throws IllegalArgumentException;
	void parse(String url, AsyncCallback<Void> callback);
	void getSpaces(AsyncCallback<ArrayList<Space>> callback);
	void getFavourites(AsyncCallback<ArrayList<String>> callback);
	void addFavourite(Space space, AsyncCallback<Void> async);
	void deleteFavourite(Space space, AsyncCallback<Void> async);
	void checkFavourite(Space space, AsyncCallback<Boolean> async);
}

