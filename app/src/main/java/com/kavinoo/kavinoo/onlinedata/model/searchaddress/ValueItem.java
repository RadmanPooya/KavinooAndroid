package com.kavinoo.kavinoo.onlinedata.model.searchaddress;

import com.google.gson.annotations.SerializedName;

public class ValueItem{

	@SerializedName("fclass")
	private String fclass;

	@SerializedName("address")
	private String address;

	@SerializedName("province")
	private String province;

	@SerializedName("city")
	private String city;

	@SerializedName("district")
	private String district;

	@SerializedName("county")
	private String county;

	@SerializedName("neighborhood")
	private String neighborhood;

	@SerializedName("region")
	private String region;

	@SerializedName("title")
	private String title;

	@SerializedName("type")
	private String type;

	@SerializedName("geom")
	private Geom geom;

	public void setFclass(String fclass){
		this.fclass = fclass;
	}

	public String getFclass(){
		return fclass;
	}

	public void setAddress(String address){
		this.address = address;
	}

	public String getAddress(){
		return address;
	}

	public void setProvince(String province){
		this.province = province;
	}

	public String getProvince(){
		return province;
	}

	public void setCity(String city){
		this.city = city;
	}

	public String getCity(){
		return city;
	}

	public void setDistrict(String district){
		this.district = district;
	}

	public String getDistrict(){
		return district;
	}

	public void setCounty(String county){
		this.county = county;
	}

	public String getCounty(){
		return county;
	}

	public void setNeighborhood(String neighborhood){
		this.neighborhood = neighborhood;
	}

	public String getNeighborhood(){
		return neighborhood;
	}

	public void setRegion(String region){
		this.region = region;
	}

	public String getRegion(){
		return region;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
	}

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return type;
	}

	public void setGeom(Geom geom){
		this.geom = geom;
	}

	public Geom getGeom(){
		return geom;
	}

	@Override
 	public String toString(){
		return 
			"ValueItem{" + 
			"fclass = '" + fclass + '\'' + 
			",address = '" + address + '\'' + 
			",province = '" + province + '\'' + 
			",city = '" + city + '\'' + 
			",district = '" + district + '\'' + 
			",county = '" + county + '\'' + 
			",neighborhood = '" + neighborhood + '\'' + 
			",region = '" + region + '\'' + 
			",title = '" + title + '\'' + 
			",type = '" + type + '\'' + 
			",geom = '" + geom + '\'' + 
			"}";
		}
}