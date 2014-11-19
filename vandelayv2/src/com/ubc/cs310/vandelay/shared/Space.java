package com.ubc.cs310.vandelay.shared;

import java.io.Serializable;

import com.google.gwt.maps.client.geom.LatLng;

public class Space implements Serializable {

	private String name;
	private String url;
	private String type;
	private String primaryUse;
	private String address;
	private String localArea;
	private String ownership;
	private double lat;
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