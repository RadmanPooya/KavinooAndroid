package com.kavinoo.kavinoo.onlinedata.model.verify;

import com.google.gson.annotations.SerializedName;

public class Data{

	@SerializedName("refresh_token")
	private String refreshToken;

	public void setRefreshToken(String refreshToken){
		this.refreshToken = refreshToken;
	}

	public String getRefreshToken(){
		return refreshToken;
	}

	@Override
 	public String toString(){
		return 
			"Data{" + 
			"refresh_token = '" + refreshToken + '\'' + 
			"}";
		}
}