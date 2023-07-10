package com.kavinoo.kavinoo.onlinedata.model.place.placelist;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Meta{

	@SerializedName("path")
	private String path;

	@SerializedName("per_page")
	private int perPage;

	@SerializedName("total")
	private int total;

	@SerializedName("last_page")
	private int lastPage;

	@SerializedName("from")
	private int from;

	@SerializedName("links")
	private List<LinksItem> links;

	@SerializedName("to")
	private int to;

	@SerializedName("current_page")
	private int currentPage;

	public void setPath(String path){
		this.path = path;
	}

	public String getPath(){
		return path;
	}

	public void setPerPage(int perPage){
		this.perPage = perPage;
	}

	public int getPerPage(){
		return perPage;
	}

	public void setTotal(int total){
		this.total = total;
	}

	public int getTotal(){
		return total;
	}

	public void setLastPage(int lastPage){
		this.lastPage = lastPage;
	}

	public int getLastPage(){
		return lastPage;
	}

	public void setFrom(int from){
		this.from = from;
	}

	public int getFrom(){
		return from;
	}

	public void setLinks(List<LinksItem> links){
		this.links = links;
	}

	public List<LinksItem> getLinks(){
		return links;
	}

	public void setTo(int to){
		this.to = to;
	}

	public int getTo(){
		return to;
	}

	public void setCurrentPage(int currentPage){
		this.currentPage = currentPage;
	}

	public int getCurrentPage(){
		return currentPage;
	}

	@Override
 	public String toString(){
		return 
			"Meta{" + 
			"path = '" + path + '\'' + 
			",per_page = '" + perPage + '\'' + 
			",total = '" + total + '\'' + 
			",last_page = '" + lastPage + '\'' + 
			",from = '" + from + '\'' + 
			",links = '" + links + '\'' + 
			",to = '" + to + '\'' + 
			",current_page = '" + currentPage + '\'' + 
			"}";
		}
}