package com.kavinoo.kavinoo.onlinedata.model.searchaddresssend;

import com.google.gson.annotations.SerializedName;

public class SendSearchAddress {

	@SerializedName("$filter")
	private String filter;

	@SerializedName("text")
	private String text;

	@SerializedName("$select")
	private String select;

	public void setFilter(String filter){
		this.filter = filter;
	}

	public String getFilter(){
		return filter;
	}

	public void setText(String text){
		this.text = text;
	}

	public String getText(){
		return text;
	}

	public void setSelect(String select){
		this.select = select;
	}

	public String getSelect(){
		return select;
	}

	@Override
 	public String toString(){
		return 
			"SendSearchAddressResponse{" + 
			"$filter = '" + filter + '\'' + 
			",text = '" + text + '\'' + 
			",$select = '" + select + '\'' + 
			"}";
		}
}