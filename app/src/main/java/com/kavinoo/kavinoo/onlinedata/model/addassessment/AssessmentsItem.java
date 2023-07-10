package com.kavinoo.kavinoo.onlinedata.model.addassessment;

import com.google.gson.annotations.SerializedName;

public class AssessmentsItem{

	@SerializedName("rate")
	private int rate;

	@SerializedName("id")
	private int id;

	public void setRate(int rate){
		this.rate = rate;
	}

	public int getRate(){
		return rate;
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
			"AssessmentsItem{" + 
			"rate = '" + rate + '\'' + 
			",id = '" + id + '\'' + 
			"}";
		}
}