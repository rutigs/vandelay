package com.ubc.cs310.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("greet")
public interface SpaceService extends RemoteService {
	String spaceServer(String name) throws IllegalArgumentException;
}
