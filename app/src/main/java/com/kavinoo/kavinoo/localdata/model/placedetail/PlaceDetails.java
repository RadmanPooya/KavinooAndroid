package com.kavinoo.kavinoo.localdata.model.placedetail;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity(tableName = "favoritePlaceTable")
public class PlaceDetails{

	@SerializedName("header_image")
	private String headerImage;

	@SerializedName("distance")
	private double distance;

	@SerializedName("latitude")
	private double latitude;

	@SerializedName("description")
	private String description;

	@SerializedName("title")
	private String title;

	@SerializedName("mobile02")
	private String mobile02;

	@SerializedName("mobile03")
	private String mobile03;

	@SerializedName("telephone01")
	private String telephone01;

	@SerializedName("rate")
	private int rate;

	@SerializedName("telephone03")
	private String telephone03;

	@SerializedName("telephone02")
	private String telephone02;

	@SerializedName("telephone04")
	private String telephone04;

	@SerializedName("favorite_count")
	private int favoriteCount;

	@PrimaryKey(autoGenerate = false)
	@SerializedName("id")
	private int id;

	@SerializedName("fax")
	private String fax;

	@SerializedName("telegram_id")
	private String telegramId;

	@SerializedName("longitude")
	private double longitude;

	@SerializedName("area")
	private Area area;

	@SerializedName("image")
	private Image image;

	@SerializedName("website")
	private String website;

	@SerializedName("visit_count")
	private int visitCount;

	@SerializedName("assessments")
	private List<AssessmentsItem> assessments;

	@SerializedName("digital_menus")
	private List<DigitalMenusItem> digitalMenus;

	@SerializedName("address")
	private String address;

	@SerializedName("checkin_count")
	private int checkinCount;

	@SerializedName("comments")
	private List<CommentsItem> comments;

	@SerializedName("instagram_id")
	private String instagramId;

	@SerializedName("mobile_primary")
	private String mobilePrimary;

	@SerializedName("comments_count")
	private int commentsCount;

	@SerializedName("category")
	private Category category;

	@SerializedName("facilities")
	private List<FacilitiesItem> facilities;

	@SerializedName("slogan")
	private String slogan;

	public void setHeaderImage(String headerImage){
		this.headerImage = headerImage;
	}

	public String getHeaderImage(){
		return headerImage;
	}

	public void setDistance(double distance){
		this.distance = distance;
	}

	public double getDistance(){
		return distance;
	}

	public void setLatitude(double latitude){
		this.latitude = latitude;
	}

	public double getLatitude(){
		return latitude;
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

	public void setMobile02(String mobile02){
		this.mobile02 = mobile02;
	}

	public String getMobile02(){
		return mobile02;
	}

	public void setMobile03(String mobile03){
		this.mobile03 = mobile03;
	}

	public String getMobile03(){
		return mobile03;
	}

	public void setTelephone01(String telephone01){
		this.telephone01 = telephone01;
	}

	public String getTelephone01(){
		return telephone01;
	}

	public void setRate(int rate){
		this.rate = rate;
	}

	public int getRate(){
		return rate;
	}

	public void setTelephone03(String telephone03){
		this.telephone03 = telephone03;
	}

	public String getTelephone03(){
		return telephone03;
	}

	public void setTelephone02(String telephone02){
		this.telephone02 = telephone02;
	}

	public String getTelephone02(){
		return telephone02;
	}

	public void setTelephone04(String telephone04){
		this.telephone04 = telephone04;
	}

	public String getTelephone04(){
		return telephone04;
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

	public void setFax(String fax){
		this.fax = fax;
	}

	public String getFax(){
		return fax;
	}

	public void setTelegramId(String telegramId){
		this.telegramId = telegramId;
	}

	public String getTelegramId(){
		return telegramId;
	}

	public void setLongitude(double longitude){
		this.longitude = longitude;
	}

	public double getLongitude(){
		return longitude;
	}

	public void setArea(Area area){
		this.area = area;
	}

	public Area getArea(){
		return area;
	}

	public void setImage(Image image){
		this.image = image;
	}

	public Image getImage(){
		return image;
	}

	public void setWebsite(String website){
		this.website = website;
	}

	public String getWebsite(){
		return website;
	}

	public void setVisitCount(int visitCount){
		this.visitCount = visitCount;
	}

	public int getVisitCount(){
		return visitCount;
	}

	public void setAssessments(List<AssessmentsItem> assessments){
		this.assessments = assessments;
	}

	public List<AssessmentsItem> getAssessments(){
		return assessments;
	}

	public void setDigitalMenus(List<DigitalMenusItem> digitalMenus){
		this.digitalMenus = digitalMenus;
	}

	public List<DigitalMenusItem> getDigitalMenus(){
		return digitalMenus;
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

	public void setComments(List<CommentsItem> comments){
		this.comments = comments;
	}

	public List<CommentsItem> getComments(){
		return comments;
	}

	public void setInstagramId(String instagramId){
		this.instagramId = instagramId;
	}

	public String getInstagramId(){
		return instagramId;
	}

	public void setMobilePrimary(String mobilePrimary){
		this.mobilePrimary = mobilePrimary;
	}

	public String getMobilePrimary(){
		return mobilePrimary;
	}

	public void setCommentsCount(int commentsCount){
		this.commentsCount = commentsCount;
	}

	public int getCommentsCount(){
		return commentsCount;
	}

	public void setCategory(Category category){
		this.category = category;
	}

	public Category getCategory(){
		return category;
	}

	public void setFacilities(List<FacilitiesItem> facilities){
		this.facilities = facilities;
	}

	public List<FacilitiesItem> getFacilities(){
		return facilities;
	}

	public void setSlogan(String slogan){
		this.slogan = slogan;
	}

	public String getSlogan(){
		return slogan;
	}

	@Override
 	public String toString(){
		return 
			"PlaceDetails{" + 
			"header_image = '" + headerImage + '\'' + 
			",distance = '" + distance + '\'' + 
			",latitude = '" + latitude + '\'' + 
			",description = '" + description + '\'' + 
			",title = '" + title + '\'' + 
			",mobile02 = '" + mobile02 + '\'' + 
			",mobile03 = '" + mobile03 + '\'' + 
			",telephone01 = '" + telephone01 + '\'' + 
			",rate = '" + rate + '\'' + 
			",telephone03 = '" + telephone03 + '\'' + 
			",telephone02 = '" + telephone02 + '\'' + 
			",telephone04 = '" + telephone04 + '\'' + 
			",favorite_count = '" + favoriteCount + '\'' + 
			",id = '" + id + '\'' + 
			",fax = '" + fax + '\'' + 
			",telegram_id = '" + telegramId + '\'' + 
			",longitude = '" + longitude + '\'' + 
			",area = '" + area + '\'' + 
			",image = '" + image + '\'' + 
			",website = '" + website + '\'' + 
			",visit_count = '" + visitCount + '\'' + 
			",assessments = '" + assessments + '\'' + 
			",digital_menus = '" + digitalMenus + '\'' + 
			",address = '" + address + '\'' + 
			",checkin_count = '" + checkinCount + '\'' + 
			",comments = '" + comments + '\'' + 
			",instagram_id = '" + instagramId + '\'' + 
			",mobile_primary = '" + mobilePrimary + '\'' + 
			",comments_count = '" + commentsCount + '\'' + 
			",category = '" + category + '\'' + 
			",facilities = '" + facilities + '\'' + 
			",slogan = '" + slogan + '\'' + 
			"}";
		}
}