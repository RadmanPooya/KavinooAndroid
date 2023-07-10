package com.kavinoo.kavinoo.onlinedata.model.verify;

import com.google.gson.annotations.SerializedName;

public class VerifyResponse{

	@SerializedName("data")
	private Data data;

	@SerializedName("statusMessage")
	private String statusMessage;

	@SerializedName("statusCode")
	private int statusCode;

	public void setData(Data data){
		this.data = data;
	}

	public Data getData(){
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
			"VerifyResponse{" + 
			"data = '" + data + '\'' + 
			",statusMessage = '" + statusMessage + '\'' + 
			",statusCode = '" + statusCode + '\'' + 
			"}";
		}
}