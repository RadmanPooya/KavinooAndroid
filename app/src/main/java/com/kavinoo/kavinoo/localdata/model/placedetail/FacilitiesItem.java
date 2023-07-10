package com.kavinoo.kavinoo.localdata.model.placedetail;

import com.google.gson.annotations.SerializedName;

public class FacilitiesItem{

	@SerializedName("name")
	private String name;

	@SerializedName("facility_id")
	private int facilityId;

	@SerializedName("id")
	private int id;

	@SerializedName("facility")
	private Facility facility;

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setFacilityId(int facilityId){
		this.facilityId = facilityId;
	}

	public int getFacilityId(){
		return facilityId;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setFacility(Facility facility){
		this.facility = facility;
	}

	public Facility getFacility(){
		return facility;
	}

	@Override
 	public String toString(){
		return 
			"FacilitiesItem{" + 
			"name = '" + name + '\'' + 
			",facility_id = '" + facilityId + '\'' + 
			",id = '" + id + '\'' + 
			",facility = '" + facility + '\'' + 
			"}";
		}
}