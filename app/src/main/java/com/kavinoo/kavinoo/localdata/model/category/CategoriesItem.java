package com.kavinoo.kavinoo.localdata.model.category;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "categoryTable")
public class CategoriesItem{

	@SerializedName("category_image")
	private String categoryImage;

	@SerializedName("pin_image")
	private String pinImage;

	@SerializedName("category_title")
	private String categoryTitle;

	@PrimaryKey(autoGenerate = false)
	@SerializedName("category_id")
	private int categoryId;

	@SerializedName("parent_id")
	private int parentId;

	@SerializedName("description")
	private String description;

	@SerializedName("has_child")
	private boolean hasChild;

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
 	public String toString(){
		return 
			"CategoriesItem{" + 
			"category_image = '" + categoryImage + '\'' + 
			",category_title = '" + categoryTitle + '\'' + 
			",category_id = '" + categoryId + '\'' + 
			",parent_id = '" + parentId + '\'' + 
			",description = '" + description + '\'' + 
			",has_child = '" + hasChild + '\'' + 
			"}";
		}
}