package com.kavinoo.kavinoo.localdata.model.placedetail;

import com.google.gson.annotations.SerializedName;

public class AssessmentsItem{

	@SerializedName("color")
	private String color;

	@SerializedName("avg_rate")
	private String avgRate;

	@SerializedName("id")
	private int id;

	@SerializedName("title")
	private String title;

	public void setColor(String color){
		this.color = color;
	}

	public String getColor(){
		return color;
	}

	public void setAvgRate(String avgRate){
		this.avgRate = avgRate;
	}

	public String getAvgRate(){
		return avgRate;
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
			"AssessmentsItem{" + 
			"color = '" + color + '\'' + 
			",avg_rate = '" + avgRate + '\'' + 
			",id = '" + id + '\'' + 
			",title = '" + title + '\'' + 
			"}";
		}
}