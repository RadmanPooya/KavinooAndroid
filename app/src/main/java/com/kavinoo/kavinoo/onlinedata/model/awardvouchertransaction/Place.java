package com.kavinoo.kavinoo.onlinedata.model.awardvouchertransaction;

import com.google.gson.annotations.SerializedName;

public class Place{

	@SerializedName("header_image")
	private String headerImage;

	@SerializedName("visit_count")
	private int visitCount;

	@SerializedName("address")
	private String address;

	@SerializedName("checkin_count")
	private int checkinCount;

	@SerializedName("distance")
	private Object distance;

	@SerializedName("latitude")
	private double latitude;

	@SerializedName("title")
	private String title;

	@SerializedName("rate")
	private Object rate;

	@SerializedName("comments_count")
	private int commentsCount;

	@SerializedName("favorite_count")
	private int favoriteCount;

	@SerializedName("id")
	private int id;

	@SerializedName("category")
	private Category category;

	@SerializedName("longitude")
	private double longitude;

	public void setHeaderImage(String headerImage){
		this.headerImage = headerImage;
	}

	public String getHeaderImage(){
		return headerImage;
	}

	public void setVisitCount(int visitCount){
		this.visitCount = visitCount;
	}

	public int getVisitCount(){
		return visitCount;
	}

	public void setAddress(String address){
		this.address = address;
	}

	public String getAddress(){
		return address;
	}

	public void setCheckinCount(int checkinCount){
		this.checkinCount = checkinCount;
	}

	public int getCheckinCount(){
		return checkinCount;
	}

	public void setDistance(Object distance){
		this.distance = distance;
	}

	public Object getDistance(){
		return distance;
	}

	public void setLatitude(double latitude){
		this.latitude = latitude;
	}

	public double getLatitude(){
		return latitude;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
	}

	public void setRate(Object rate){
		this.rate = rate;
	}

	public Object getRate(){
		return rate;
	}

	public void setCommentsCount(int commentsCount){
		this.commentsCount = commentsCount;
	}

	public int getCommentsCount(){
		return commentsCount;
	}

	public void setFavoriteCount(int favoriteCount){
		this.favoriteCount = favoriteCount;
	}

	public int getFavoriteCount(){
		return favoriteCount;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setCategory(Category category){
		this.category = category;
	}

	public Category getCategory(){
		return category;
	}

	public void setLongitude(double longitude){
		this.longitude = longitude;
	}

	public double getLongitude(){
		return longitude;
	}

	@Override
 	public String toString(){
		return 
			"Place{" + 
			"header_image = '" + headerImage + '\'' + 
			",visit_count = '" + visitCount + '\'' + 
			",address = '" + address + '\'' + 
			",checkin_count = '" + checkinCount + '\'' + 
			",distance = '" + distance + '\'' + 
			",latitude = '" + latitude + '\'' + 
			",title = '" + title + '\'' + 
			",rate = '" + rate + '\'' + 
			",comments_count = '" + commentsCount + '\'' + 
			",favorite_count = '" + favoriteCount + '\'' + 
			",id = '" + id + '\'' + 
			",category = '" + category + '\'' + 
			",longitude = '" + longitude + '\'' + 
			"}";
		}
}