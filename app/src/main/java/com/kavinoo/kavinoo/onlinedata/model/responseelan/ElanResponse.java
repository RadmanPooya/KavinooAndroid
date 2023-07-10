package com.kavinoo.kavinoo.onlinedata.model.responseelan;

import com.google.gson.annotations.SerializedName;

public class ElanResponse{

	@SerializedName("data")
	private Object data;

	@SerializedName("statusMessage")
	private String statusMessage;

	@SerializedName("statusCode")
	private int statusCode;

	public void setData(Object data){
		this.data = data;
	}

	public Object getData(){
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
			"ElanResponse{" + 
			"data = '" + data + '\'' + 
			",statusMessage = '" + statusMessage + '\'' + 
			",statusCode = '" + statusCode + '\'' + 
			"}";
		}
}