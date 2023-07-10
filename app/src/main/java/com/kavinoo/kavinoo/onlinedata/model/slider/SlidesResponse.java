package com.kavinoo.kavinoo.onlinedata.model.slider;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class SlidesResponse{

	@SerializedName("slider")
	private List<SliderItem> slider;

	@SerializedName("statusMessage")
	private String statusMessage;

	@SerializedName("statusCode")
	private int statusCode;

	public void setSlider(List<SliderItem> slider){
		this.slider = slider;
	}

	public List<SliderItem> getSlider(){
		return slider;
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
			"SlidesResponse{" + 
			"slider = '" + slider + '\'' + 
			",statusMessage = '" + statusMessage + '\'' + 
			",statusCode = '" + statusCode + '\'' + 
			"}";
		}
}