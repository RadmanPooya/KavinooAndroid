package com.kavinoo.kavinoo.onlinedata.model.awardvoucher;

import com.google.gson.annotations.SerializedName;

public class Parent{

	@SerializedName("category_image")
	private String categoryImage;

	@SerializedName("pin_image")
	private String pinImage;

	@SerializedName("parent")
	private Object parent;

	@SerializedName("category_title")
	private String categoryTitle;

	@SerializedName("category_id")
	private int categoryId;

	@SerializedName("parent_id")
	private Object parentId;

	@SerializedName("description")
	private Object description;

	@SerializedName("has_child")
	private boolean hasChild;

	public void setCategoryImage(String categoryImage){
		this.categoryImage = categoryImage;
	}

	public String getCategoryImage(){
		return categoryImage;
	}

	public void setPinImage(String pinImage){
		this.pinImage = pinImage;
	}

	public String getPinImage(){
		return pinImage;
	}

	public void setParent(Object parent){
		this.parent = parent;
	}

	public Object getParent(){
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

	public void setParentId(Object parentId){
		this.parentId = parentId;
	}

	public Object getParentId(){
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
			"Parent{" + 
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