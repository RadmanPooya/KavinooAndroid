package com.kavinoo.kavinoo.onlinedata.model.activityscoretransaction;

import com.google.gson.annotations.SerializedName;

public class DataItem{

	@SerializedName("activity_score")
	private ActivityScore activityScore;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("id")
	private int id;

	@SerializedName("place")
	private Place place;

	public void setActivityScore(ActivityScore activityScore){
		this.activityScore = activityScore;
	}

	public ActivityScore getActivityScore(){
		return activityScore;
	}

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setPlace(Place place){
		this.place = place;
	}

	public Place getPlace(){
		return place;
	}

	@Override
 	public String toString(){
		return 
			"DataItem{" + 
			"activity_score = '" + activityScore + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",id = '" + id + '\'' + 
			",place = '" + place + '\'' + 
			"}";
		}
}