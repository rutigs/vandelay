package com.ubc.cs310.server;

import java.util.Date;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import com.google.appengine.api.users.User;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Space {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;
	@Persistent
	private User user;
	@Persistent
	private String title;
	@Persistent
	private Date createDate;
	// Fix fields for needed information
	
	public Space() {
		// Do nothing?
	}
	
	public Space(User user, String title) {
		this();
		this.user = user;
		this.title = title;
		// add other fields - get them from parser
	}
	
	public User getUser() {
	    return this.user;
	}
	
	public String getTitle() {
	    return this.title;
	}
	
	public void setUser(User user) {
	    this.user = user;
	}
	
	public void setTitle(String title) {
	    this.title = title;
	}
	
	// Code the rest of the getters and setters needed for fields above
}
