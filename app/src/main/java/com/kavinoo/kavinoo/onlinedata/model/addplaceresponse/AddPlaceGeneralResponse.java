package com.kavinoo.kavinoo.onlinedata.model.addplaceresponse;

import com.google.gson.annotations.SerializedName;

public class AddPlaceGeneralResponse{

	@SerializedName("data")
	private int data;

	@SerializedName("statusMessage")
	private String statusMessage;

	@SerializedName("statusCode")
	private int statusCode;

	public void setData(int data){
		this.data = data;
	}

	public int getData(){
		return data;
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
			"AddPlaceGeneralResponse{" + 
			"data = '" + data + '\'' + 
			",statusMessage = '" + statusMessage + '\'' + 
			",statusCode = '" + statusCode + '\'' + 
			"}";
		}
}