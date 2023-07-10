package com.kavinoo.kavinoo.onlinedata.model.place.placedetail;

import com.google.gson.annotations.SerializedName;

public class CommentsItem{

	@SerializedName("id")
	private int id;

	@SerializedName("title")
	private String title;

	@SerializedName("body")
	private String body;

	@SerializedName("user")
	private String user;

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
	}

	public void setBody(String body){
		this.body = body;
	}

	public String getBody(){
		return body;
	}

	public void setUser(String user){
		this.user = user;
	}

	public String getUser(){
		return user;
	}

	@Override
 	public String toString(){
		return 
			"CommentsItem{" + 
			"id = '" + id + '\'' + 
			",title = '" + title + '\'' + 
			",body = '" + body + '\'' + 
			",user = '" + user + '\'' + 
			"}";
		}
}