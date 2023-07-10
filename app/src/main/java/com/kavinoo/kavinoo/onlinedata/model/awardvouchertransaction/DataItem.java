package com.kavinoo.kavinoo.onlinedata.model.awardvouchertransaction;

import com.google.gson.annotations.SerializedName;

public class DataItem{

	@SerializedName("award_voucher")
	private AwardVoucher awardVoucher;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("id")
	private int id;

	public void setAwardVoucher(AwardVoucher awardVoucher){
		this.awardVoucher = awardVoucher;
	}

	public AwardVoucher getAwardVoucher(){
		return awardVoucher;
	}

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	@Override
 	public String toString(){
		return 
			"DataItem{" + 
			"award_voucher = '" + awardVoucher + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",id = '" + id + '\'' + 
			"}";
		}
}