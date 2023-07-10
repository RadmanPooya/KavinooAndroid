package com.kavinoo.kavinoo.onlinedata.model.place.placedetail;

import com.google.gson.annotations.SerializedName;

public class DigitalMenusItem{

	@SerializedName("image")
	private String image;

	@SerializedName("price")
	private int price;

	@SerializedName("description")
	private String description;

	@SerializedName("title")
	private String title;

	public void setImage(String image){
		this.image = image;
	}

	public String getImage(){
		return image;
	}

	public void setPrice(int price){
		this.price = price;
	}

	public int getPrice(){
		return price;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return description;
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
			"DigitalMenusItem{" + 
			"image = '" + image + '\'' + 
			",price = '" + price + '\'' + 
			",description = '" + description + '\'' + 
			",title = '" + title + '\'' + 
			"}";
		}
}