package com.kavinoo.kavinoo.localdata.model.category;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class CategoriesResponse{

	@SerializedName("categories")
	private List<CategoriesItem> categories;

	@SerializedName("statusMessage")
	private String statusMessage;

	@SerializedName("statusCode")
	private int statusCode;

	public void setCategories(List<CategoriesItem> categories){
		this.categories = categories;
	}

	public List<CategoriesItem> getCategories(){
		return categories;
	}

	public void setStatusMessage(String statusMessage){
		this.statusMessage = statusMessage;
	}

	public String getStatusMessage(){
		return statusMessage;
	}

	public void setStatusCode(int statusCode){
		this.statusCode = statusCode;
	}

	public int getStatusCode(){
		return statusCode;
	}

	@Override
 	public String toString(){
		return 
			"CategoriesResponse{" + 
			"categories = '" + categories + '\'' + 
			",statusMessage = '" + statusMessage + '\'' + 
			",statusCode = '" + statusCode + '\'' + 
			"}";
		}
}