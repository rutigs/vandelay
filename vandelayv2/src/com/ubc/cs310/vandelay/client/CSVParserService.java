package com.ubc.cs310.vandelay.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.ubc.cs310.vandelay.shared.Space;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("parse")
public interface CSVParserService extends RemoteService {
	String CSVParse(String name) throws IllegalArgumentException;
	void parse(String url);
	ArrayList<Space> getSpaces();
	ArrayList<String> getFavourites();
	void addFavourite(Space space);
	void deleteFavourite(Space space);
}