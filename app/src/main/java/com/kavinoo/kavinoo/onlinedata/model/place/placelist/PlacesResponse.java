package com.kavinoo.kavinoo.onlinedata.model.place.placelist;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class PlacesResponse{

	@SerializedName("places")
	private List<PlacesItem> places;

	@SerializedName("meta")
	private Meta meta;

	@SerializedName("links")
	private Links links;

	@SerializedName("statusMessage")
	private String statusMessage;

	@SerializedName("statusCode")
	private int statusCode;

	public void setPlaces(List<PlacesItem> places){
		this.places = places;
	}

	public List<PlacesItem> getPlaces(){
		return places;
	}

	public void setMeta(Meta meta){
		this.meta = meta;
	}

	public Meta getMeta(){
		return meta;
	}

	public void setLinks(Links links){
		this.links = links;
	}

	public Links getLinks(){
		return links;
	}

	public void setStatusMessage(String statusMessage){
		this.statusMessage = statusMessage;
	}

	public String getStatusMessage(){
		return statusMessage;
	}

	public void setStatusCode(int statusCode){
		this.statusCode = statusCode;
	}

	public int getStatusCode(){
		return statusCode;
	}

	@Override
 	public String toString(){
		return 
			"PlacesResponse{" + 
			"places = '" + places + '\'' + 
			",meta = '" + meta + '\'' + 
			",links = '" + links + '\'' + 
			",statusMessage = '" + statusMessage + '\'' + 
			",statusCode = '" + statusCode + '\'' + 
			"}";
		}
}