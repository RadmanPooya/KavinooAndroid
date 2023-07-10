package com.kavinoo.kavinoo.onlinedata.model.awardvoucher;

import com.google.gson.annotations.SerializedName;

public class DataItem{

	@SerializedName("image")
	private String image;

	@SerializedName("score")
	private int score;

	@SerializedName("data")
	private String data;

	@SerializedName("description")
	private String description;

	@SerializedName("id")
	private int id;

	@SerializedName("place")
	private Object place;

	@SerializedName("title")
	private String title;

	@SerializedName("type")
	private int type;

	public void setImage(String image){
		this.image = image;
	}

	public String getImage(){
		return image;
	}

	public void setScore(int score){
		this.score = score;
	}

	public int getScore(){
		return score;
	}

	public void setData(String data){
		this.data = data;
	}

	public String getData(){
		return data;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return description;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setPlace(Object place){
		this.place = place;
	}

	public Object getPlace(){
		return place;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
	}

	public void setType(int type){
		this.type = type;
	}

	public int getType(){
		return type;
	}

	@Override
 	public String toString(){
		return 
			"DataItem{" + 
			"image = '" + image + '\'' + 
			",score = '" + score + '\'' + 
			",data = '" + data + '\'' + 
			",description = '" + description + '\'' + 
			",id = '" + id + '\'' + 
			",place = '" + place + '\'' + 
			",title = '" + title + '\'' + 
			",type = '" + type + '\'' + 
			"}";
		}
}