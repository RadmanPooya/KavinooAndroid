package com.kavinoo.kavinoo.onlinedata.model.token;

import com.google.gson.annotations.SerializedName;

public class User{

	@SerializedName("score")
	private int score;

	@SerializedName("gender")
	private String gender;

	@SerializedName("money")
	private int money;

	@SerializedName("user_id")
	private int userId;

	@SerializedName("mobile")
	private String mobile;

	@SerializedName("name")
	private String name;

	@SerializedName("family")
	private Object family;

	@SerializedName("email")
	private Object email;

	public void setScore(int score){
		this.score = score;
	}

	public int getScore(){
		return score;
	}

	public void setGender(String gender){
		this.gender = gender;
	}

	public String getGender(){
		return gender;
	}

	public void setMoney(int money){
		this.money = money;
	}

	public int getMoney(){
		return money;
	}

	public void setUserId(int userId){
		this.userId = userId;
	}

	public int getUserId(){
		return userId;
	}

	public void setMobile(String mobile){
		this.mobile = mobile;
	}

	public String getMobile(){
		return mobile;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setFamily(Object family){
		this.family = family;
	}

	public Object getFamily(){
		return family;
	}

	public void setEmail(Object email){
		this.email = email;
	}

	public Object getEmail(){
		return email;
	}

	@Override
 	public String toString(){
		return 
			"User{" + 
			"score = '" + score + '\'' + 
			",gender = '" + gender + '\'' + 
			",money = '" + money + '\'' + 
			",user_id = '" + userId + '\'' + 
			",mobile = '" + mobile + '\'' + 
			",name = '" + name + '\'' + 
			",family = '" + family + '\'' + 
			",email = '" + email + '\'' + 
			"}";
		}
}