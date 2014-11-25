package com.ubc.cs310.vandelay.shared;

import java.io.Serializable;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Space implements Serializable {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;
	@Persistent
	private String name;
	@Persistent
	private String url;
	@Persistent
	private String type;
	@Persistent
	private String primaryUse;
	@Persistent
	private String address;
	@Persistent
	private String localArea;
	@Persistent
	private String ownership;
	@Persistent
	private double lat;
	@Persistent
	private double lon;

	public Space(){
		this.name = "";
		this.url = "";
		this.type = "";
		this.primaryUse = "";
		this.address = "";
		this.localArea = "";
		this.ownership = "";
	}

	public Space(String name, String url, String type, String primaryUse, String address, 
			String localArea, String ownership, double lat, double lon) {
		this.name = name;
		this.url = url;
		this.type = type;
		this.primaryUse = primaryUse;
		this.address = address;
		this.localArea = localArea;
		this.ownership = ownership;
		this.lat = lat;
		this.lon = lon;
	}
	public Long getId() {
		return this.id;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public void setURL(String url) {
		this.url = url;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setPrimaryUse(String pUse) {
		this.primaryUse = pUse;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setLocalArea(String lArea) {
		this.localArea = lArea;
	}

	public void setOwnership(String ownership) {
		this.ownership = ownership;
	}
	

	public void setLat(double lat) {
		this.lat = lat;
	}
	
	public void setLon(double lon) {
		this.lon = lon;
	}

	public String getName() {
		return this.name;
	}

	public String getURL() {
		return this.url;
	}

	public String getType() {
		return this.type;
	}

	public String getPrimaryUse() {
		return this.primaryUse;
	}

	public String getAddress() {
		return this.address;
	}

	public String getLocalArea() {
		return this.localArea;
	}

	public String getOwnerShip() {
		return this.ownership;
	}

	public double getLat() {
		return this.lat;
	}
	
	public double getLon() {
		return this.lon;
	}
}