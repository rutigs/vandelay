package com.ubc.cs310.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("parse")
public interface CSVParser extends RemoteService {
	String CSVParse(String name) throws IllegalArgumentException;
}
