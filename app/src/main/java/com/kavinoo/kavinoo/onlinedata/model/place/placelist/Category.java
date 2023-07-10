package com.kavinoo.kavinoo.onlinedata.model.place.placelist;

import com.google.gson.annotations.SerializedName;

public class Category{

	@SerializedName("category_image")
	private String categoryImage;

	@SerializedName("pin_image")
	private String pinImage;

	@SerializedName("category_title")
	private String categoryTitle;

	@SerializedName("category_id")
	private int categoryId;

	@SerializedName("parent_id")
	private int parentId;

	@SerializedName("description")
	private String description;

	@SerializedName("has_child")
	private boolean hasChild;

	@SerializedName("parent")
	private Category parent;

	public Category getParent() {
		return parent;
	}

	public void setParent(Category parent) {
		this.parent = parent;
	}

	public void setCategoryImage(String categoryImage){
		this.categoryImage = categoryImage;
	}

	public void setPinImage(String pinImage) {
		this.pinImage = pinImage;
	}

	public String getCategoryImage(){
		return categoryImage;
	}

	public String getPinImage() {
		return pinImage;
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

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return description;
	}

	public void setHasChild(boolean hasChild){
		this.hasChild = hasChild;
	}

	public boolean isHasChild(){
		return hasChild;
	}

	@Override
	public String toString() {
		return "Category{" +
				"categoryImage='" + categoryImage + '\'' +
				", pinImage='" + pinImage + '\'' +
				", categoryTitle='" + categoryTitle + '\'' +
				", categoryId=" + categoryId +
				", parentId=" + parentId +
				", description='" + description + '\'' +
				", hasChild=" + hasChild +
				", parent=" + parent +
				'}';
	}
}