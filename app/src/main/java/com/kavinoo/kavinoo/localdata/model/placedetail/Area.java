package com.kavinoo.kavinoo.localdata.model.placedetail;

import com.google.gson.annotations.SerializedName;

public class Area{

	@SerializedName("parent_id")
	private int parentId;

	@SerializedName("latitude")
	private double latitude;

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private int id;

	@SerializedName("longitude")
	private double longitude;

	public void setParentId(int parentId){
		this.parentId = parentId;
	}

	public int getParentId(){
		return parentId;
	}

	public void setLatitude(double latitude){
		this.latitude = latitude;
	}

	public double getLatitude(){
		return latitude;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setLongitude(double longitude){
		this.longitude = longitude;
	}

	public double getLongitude(){
		return longitude;
	}

	@Override
 	public String toString(){
		return 
			"Area{" + 
			"parent_id = '" + parentId + '\'' + 
			",latitude = '" + latitude + '\'' + 
			",name = '" + name + '\'' + 
			",id = '" + id + '\'' + 
			",longitude = '" + longitude + '\'' + 
			"}";
		}
}