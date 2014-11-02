package com.ubc.cs310.server;

public class Space{

private String name;
private String url;
private String type;
private String primaryUse;
private String address;
private String localArea;
private String ownership;
//private Location location;

public Space(){
}

public Space(String name, String url, String type, String primaryUse, String address, 
		String localArea, String ownership) {
	this.name = name;
	this.url = url;
	this.type = type;
	this.primaryUse = primaryUse;
	this.address = address;
	this.localArea = localArea;
	this.ownership = ownership;
}

public void setName(String name){
	this.name = name;
}

public void setURL(String url){
	this.url = url;
}

public void setType(String type){
	this.type = type;
}

public void setPrimaryUse(String pUse){
	this.primaryUse = pUse;
}

public void setAddress(String address){
	this.address = address;
}

public void setLocalArea(String lArea){
	this.localArea = lArea;
}

public void setOwnership(String ownership){
	this.ownership = ownership;
}

/*public void setLocation(String address, float lat, float lon){
	this.location = new Location(address, lat, lon)
}*/

public String getName(){
	return this.name;
}

public String getURL(){
	return this.url;
}

public String getType(){
	return this.type;
}

public String getPrimaryUse(){
	return this.primaryUse;
}

public String getAddress(){
	return this.address;
}

public String getLocalArea(){
	return this.localArea;
}

public String getOwnerShip(){
	return this.ownership;
}

/*public Location getLocation(){
	return this.location;
}
*/
}