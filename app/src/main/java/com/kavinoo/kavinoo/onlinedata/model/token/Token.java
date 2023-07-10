package com.kavinoo.kavinoo.onlinedata.model.token;

import com.google.gson.annotations.SerializedName;

public class Token{

	@SerializedName("refresh_token")
	private String refreshToken;

	@SerializedName("id")
	private int id;

	@SerializedName("expire_datetime")
	private int expireDatetime;

	@SerializedName("token")
	private String token;

	public void setRefreshToken(String refreshToken){
		this.refreshToken = refreshToken;
	}

	public String getRefreshToken(){
		return refreshToken;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setExpireDatetime(int expireDatetime){
		this.expireDatetime = expireDatetime;
	}

	public int getExpireDatetime(){
		return expireDatetime;
	}

	public void setToken(String token){
		this.token = token;
	}

	public String getToken(){
		return token;
	}

	@Override
 	public String toString(){
		return 
			"Token{" + 
			"refresh_token = '" + refreshToken + '\'' + 
			",id = '" + id + '\'' + 
			",expire_datetime = '" + expireDatetime + '\'' + 
			",token = '" + token + '\'' + 
			"}";
		}
}