package com.kavinoo.kavinoo.onlinedata.model.getaddressresponse;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Geom{

	@SerializedName("coordinates")
	private List<String> coordinates;

	@SerializedName("type")
	private String type;

	public void setCoordinates(List<String> coordinates){
		this.coordinates = coordinates;
	}

	public List<String> getCoordinates(){
		return coordinates;
	}

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return type;
	}

	@Override
 	public String toString(){
		return 
			"Geom{" + 
			"coordinates = '" + coordinates + '\'' + 
			",type = '" + type + '\'' + 
			"}";
		}
}