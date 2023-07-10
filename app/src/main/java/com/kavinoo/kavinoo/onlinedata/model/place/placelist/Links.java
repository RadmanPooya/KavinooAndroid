package com.kavinoo.kavinoo.onlinedata.model.place.placelist;

import com.google.gson.annotations.SerializedName;

public class Links{

	@SerializedName("next")
	private String next;

	@SerializedName("last")
	private String last;

	@SerializedName("prev")
	private String prev;

	@SerializedName("first")
	private String first;

	public void setNext(String next){
		this.next = next;
	}

	public String getNext(){
		return next;
	}

	public void setLast(String last){
		this.last = last;
	}

	public String getLast(){
		return last;
	}

	public void setPrev(String prev){
		this.prev = prev;
	}

	public String getPrev(){
		return prev;
	}

	public void setFirst(String first){
		this.first = first;
	}

	public String getFirst(){
		return first;
	}

	@Override
 	public String toString(){
		return 
			"Links{" + 
			"next = '" + next + '\'' + 
			",last = '" + last + '\'' + 
			",prev = '" + prev + '\'' + 
			",first = '" + first + '\'' + 
			"}";
		}
}