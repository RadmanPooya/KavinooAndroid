package com.kavinoo.kavinoo.onlinedata.model.facilitiesfilter;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class FacilitiesResponse{

	@SerializedName("facilities")
	private List<FacilitiesItem> facilities;

	@SerializedName("statusMessage")
	private String statusMessage;

	@SerializedName("statusCode")
	private int statusCode;

	public void setFacilities(List<FacilitiesItem> facilities){
		this.facilities = facilities;
	}

	public List<FacilitiesItem> getFacilities(){
		return facilities;
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
			"FacilitiesResponse{" + 
			"facilities = '" + facilities + '\'' + 
			",statusMessage = '" + statusMessage + '\'' + 
			",statusCode = '" + statusCode + '\'' + 
			"}";
		}
}