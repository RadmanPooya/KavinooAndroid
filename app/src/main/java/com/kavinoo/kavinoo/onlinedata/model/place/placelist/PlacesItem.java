package com.kavinoo.kavinoo.onlinedata.model.place.placelist;

import com.google.gson.annotations.SerializedName;

public class PlacesItem{

	@SerializedName("header_image")
	private String headerImage;

	@SerializedName("visit_count")
	private int visitCount;

	@SerializedName("address")
	private String address;

	@SerializedName("distance")
	private double distance;

	@SerializedName("rate")
	private double rate;

	@SerializedName("comments_count")
	private int commentsCount;

	@SerializedName("latitude")
	private double latitude;

	@SerializedName("favorite_count")
	private int favoriteCount;

	@SerializedName("id")
	private int id;

	@SerializedName("title")
	private String title;

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

	public void setDistance(double distance){
		this.distance = distance;
	}

	public double getDistance(){
		return distance;
	}

	public void setRate(double rate){
		this.rate = rate;
	}

	public double getRate(){
		return rate;
	}

	public void setCommentsCount(int commentsCount){
		this.commentsCount = commentsCount;
	}

	public int getCommentsCount(){
		return commentsCount;
	}

	public void setLatitude(double latitude){
		this.latitude = latitude;
	}

	public double getLatitude(){
		return latitude;
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

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
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
			"PlacesItem{" + 
			"header_image = '" + headerImage + '\'' + 
			",visit_count = '" + visitCount + '\'' + 
			",address = '" + address + '\'' + 
			",distance = '" + distance + '\'' + 
			",rate = '" + rate + '\'' + 
			",comments_count = '" + commentsCount + '\'' + 
			",latitude = '" + latitude + '\'' + 
			",favorite_count = '" + favoriteCount + '\'' + 
			",id = '" + id + '\'' + 
			",title = '" + title + '\'' + 
			",category = '" + category + '\'' + 
			",longitude = '" + longitude + '\'' + 
			"}";
		}
}