package com.kavinoo.kavinoo.onlinedata.model.slider;

import com.google.gson.annotations.SerializedName;

public class SliderItem{

	@SerializedName("open_type")
	private String openType;

	@SerializedName("data_id")
	private int dataId;

	@SerializedName("imageAddress")
	private String imageAddress;

	@SerializedName("type")
	private Object type;

	@SerializedName("data_key")
	private String dataKey;

	@SerializedName("url")
	private String url;

	public void setOpenType(String openType){
		this.openType = openType;
	}

	public String getOpenType(){
		return openType;
	}

	public void setDataId(int dataId){
		this.dataId = dataId;
	}

	public int getDataId(){
		return dataId;
	}

	public void setImageAddress(String imageAddress){
		this.imageAddress = imageAddress;
	}

	public String getImageAddress(){
		return imageAddress;
	}

	public void setType(Object type){
		this.type = type;
	}

	public Object getType(){
		return type;
	}

	public void setDataKey(String dataKey){
		this.dataKey = dataKey;
	}

	public String getDataKey(){
		return dataKey;
	}

	public void setUrl(String url){
		this.url = url;
	}

	public String getUrl(){
		return url;
	}

	@Override
 	public String toString(){
		return 
			"SliderItem{" + 
			"open_type = '" + openType + '\'' + 
			",data_id = '" + dataId + '\'' + 
			",imageAddress = '" + imageAddress + '\'' + 
			",type = '" + type + '\'' + 
			",data_key = '" + dataKey + '\'' + 
			",url = '" + url + '\'' + 
			"}";
		}
}