package com.kavinoo.kavinoo.onlinedata.model.awardvouchertransaction;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class AwardVoucherTransactionResponse{

	@SerializedName("data")
	private List<DataItem> data;

	public void setData(List<DataItem> data){
		this.data = data;
	}

	public List<DataItem> getData(){
		return data;
	}

	@Override
 	public String toString(){
		return 
			"AwardVoucherTransactionResponse{" + 
			"data = '" + data + '\'' + 
			"}";
		}
}