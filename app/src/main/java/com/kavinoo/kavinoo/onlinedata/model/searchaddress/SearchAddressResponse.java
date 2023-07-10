package com.kavinoo.kavinoo.onlinedata.model.searchaddress;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchAddressResponse{

	@SerializedName("odata.count")
	private int odataCount;

	@SerializedName("value")
	private List<ValueItem> value;

	public void setOdataCount(int odataCount){
		this.odataCount = odataCount;
	}

	public int getOdataCount(){
		return odataCount;
	}

	public void setValue(List<ValueItem> value){
		this.value = value;
	}

	public List<ValueItem> getValue(){
		return value;
	}

	@Override
 	public String toString(){
		return 
			"SearchAddressResponse{" + 
			"odata.count = '" + odataCount + '\'' + 
			",value = '" + value + '\'' + 
			"}";
		}
}