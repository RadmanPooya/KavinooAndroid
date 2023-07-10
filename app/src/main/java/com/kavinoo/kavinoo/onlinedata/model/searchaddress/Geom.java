package com.kavinoo.kavinoo.onlinedata.model.searchaddress;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Geom{

	@SerializedName("coordinates")
	private List<Double> coordinates;

	@SerializedName("type")
	private String type;

	public void setCoordinates(List<Double> coordinates){
		this.coordinates = coordinates;
	}

	public List<Double> getCoordinates(){
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