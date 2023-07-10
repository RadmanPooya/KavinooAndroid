package com.kavinoo.kavinoo.onlinedata.model.activityscore;

import com.google.gson.annotations.SerializedName;

public class DataItem{

	@SerializedName("score")
	private int score;

	@SerializedName("image")
	private String image;

	@SerializedName("id")
	private int id;

	@SerializedName("title")
	private String title;

	public void setScore(int score){
		this.score = score;
	}

	public int getScore(){
		return score;
	}

	public void setImage(String image){
		this.image = image;
	}

	public String getImage(){
		return image;
	}

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

	@Override
 	public String toString(){
		return 
			"DataItem{" + 
			"score = '" + score + '\'' + 
			",image = '" + image + '\'' + 
			",id = '" + id + '\'' + 
			",title = '" + title + '\'' + 
			"}";
		}
}