package com.kavinoo.kavinoo.onlinedata.model.awardvouchertransaction;

import com.google.gson.annotations.SerializedName;

public class Category{

	@SerializedName("category_image")
	private Object categoryImage;

	@SerializedName("pin_image")
	private Object pinImage;

	@SerializedName("parent")
	private Parent parent;

	@SerializedName("category_title")
	private String categoryTitle;

	@SerializedName("category_id")
	private int categoryId;

	@SerializedName("parent_id")
	private int parentId;

	@SerializedName("description")
	private Object description;

	@SerializedName("has_child")
	private boolean hasChild;

	public void setCategoryImage(Object categoryImage){
		this.categoryImage = categoryImage;
	}

	public Object getCategoryImage(){
		return categoryImage;
	}

	public void setPinImage(Object pinImage){
		this.pinImage = pinImage;
	}

	public Object getPinImage(){
		return pinImage;
	}

	public void setParent(Parent parent){
		this.parent = parent;
	}

	public Parent getParent(){
		return parent;
	}

	public void setCategoryTitle(String categoryTitle){
		this.categoryTitle = categoryTitle;
	}

	public String getCategoryTitle(){
		return categoryTitle;
	}

	public void setCategoryId(int categoryId){
		this.categoryId = categoryId;
	}

	public int getCategoryId(){
		return categoryId;
	}

	public void setParentId(int parentId){
		this.parentId = parentId;
	}

	public int getParentId(){
		return parentId;
	}

	public void setDescription(Object description){
		this.description = description;
	}

	public Object getDescription(){
		return description;
	}

	public void setHasChild(boolean hasChild){
		this.hasChild = hasChild;
	}

	public boolean isHasChild(){
		return hasChild;
	}

	@Override
 	public String toString(){
		return 
			"Category{" + 
			"category_image = '" + categoryImage + '\'' + 
			",pin_image = '" + pinImage + '\'' + 
			",parent = '" + parent + '\'' + 
			",category_title = '" + categoryTitle + '\'' + 
			",category_id = '" + categoryId + '\'' + 
			",parent_id = '" + parentId + '\'' + 
			",description = '" + description + '\'' + 
			",has_child = '" + hasChild + '\'' + 
			"}";
		}
}