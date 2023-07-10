package com.kavinoo.kavinoo.onlinedata.model.place.placedetail;

import com.google.gson.annotations.SerializedName;

public class Image{

	@SerializedName("header_image")
	private String headerImage;

	@SerializedName("image02")
	private String image02;

	@SerializedName("image01")
	private String image01;

	@SerializedName("image04")
	private String image04;

	@SerializedName("image03")
	private String image03;

	@SerializedName("video")
	private String video;

	@SerializedName("image10")
	private String image10;

	@SerializedName("image09")
	private String image09;

	@SerializedName("panorama_image")
	private String panoramaImage;

	@SerializedName("image06")
	private String image06;

	@SerializedName("image05")
	private String image05;

	@SerializedName("image08")
	private String image08;

	@SerializedName("image07")
	private String image07;

	@SerializedName("id")
	private int id;

	@SerializedName("place_id")
	private int placeId;

	public void setHeaderImage(String headerImage){
		this.headerImage = headerImage;
	}

	public String getHeaderImage(){
		return headerImage;
	}

	public void setImage02(String image02){
		this.image02 = image02;
	}

	public String getImage02(){
		return image02;
	}

	public void setImage01(String image01){
		this.image01 = image01;
	}

	public String getImage01(){
		return image01;
	}

	public void setImage04(String image04){
		this.image04 = image04;
	}

	public String getImage04(){
		return image04;
	}

	public void setImage03(String image03){
		this.image03 = image03;
	}

	public String getImage03(){
		return image03;
	}

	public void setVideo(String video){
		this.video = video;
	}

	public String getVideo(){
		return video;
	}

	public void setImage10(String image10){
		this.image10 = image10;
	}

	public String getImage10(){
		return image10;
	}

	public void setImage09(String image09){
		this.image09 = image09;
	}

	public String getImage09(){
		return image09;
	}

	public void setPanoramaImage(String panoramaImage){
		this.panoramaImage = panoramaImage;
	}

	public String getPanoramaImage(){
		return panoramaImage;
	}

	public void setImage06(String image06){
		this.image06 = image06;
	}

	public String getImage06(){
		return image06;
	}

	public void setImage05(String image05){
		this.image05 = image05;
	}

	public String getImage05(){
		return image05;
	}

	public void setImage08(String image08){
		this.image08 = image08;
	}

	public String getImage08(){
		return image08;
	}

	public void setImage07(String image07){
		this.image07 = image07;
	}

	public String getImage07(){
		return image07;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setPlaceId(int placeId){
		this.placeId = placeId;
	}

	public int getPlaceId(){
		return placeId;
	}

	@Override
 	public String toString(){
		return 
			"Image{" + 
			"header_image = '" + headerImage + '\'' + 
			",image02 = '" + image02 + '\'' + 
			",image01 = '" + image01 + '\'' + 
			",image04 = '" + image04 + '\'' + 
			",image03 = '" + image03 + '\'' + 
			",video = '" + video + '\'' + 
			",image10 = '" + image10 + '\'' + 
			",image09 = '" + image09 + '\'' + 
			",panorama_image = '" + panoramaImage + '\'' + 
			",image06 = '" + image06 + '\'' + 
			",image05 = '" + image05 + '\'' + 
			",image08 = '" + image08 + '\'' + 
			",image07 = '" + image07 + '\'' + 
			",id = '" + id + '\'' + 
			",place_id = '" + placeId + '\'' + 
			"}";
		}
}