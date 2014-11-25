package com.ubc.cs310.vandelay.server;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.users.User;
import com.ubc.cs310.vandelay.shared.Space;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class FavouriteSpace {
	  
	  @PrimaryKey
	  @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	  private Long id;
	  @Persistent
	  private User user;
	  @Persistent
	  private String space;
	  
	  public FavouriteSpace(User user, String space) {
		  this.user = user;
		  this.space = space;
	  }
	  
	  public long getId() {
		  return this.id;
	  }
	  
	  public User getUser() {
		  return this.user;
	  }
	  
	  public String getSpace() {
		  return this.space;
	  }
	  
	  public void setUser(User user) {
		  this.user = user;
	  }
	  
	  public void setSpace(String space) {
		  this.space = space;
	  }
}
