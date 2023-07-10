package com.kavinoo.kavinoo.onlinedata.model.getaddressresponse;

import com.google.gson.annotations.SerializedName;

public class GetAddressResponse{

	@SerializedName("postal_address")
	private String postalAddress;

	@SerializedName("country")
	private String country;

	@SerializedName("plaque")
	private String plaque;

	@SerializedName("address")
	private String address;

	@SerializedName("last")
	private String last;

	@SerializedName("address_compact")
	private String addressCompact;

	@SerializedName("city")
	private String city;

	@SerializedName("county")
	private String county;

	@SerializedName("poi")
	private String poi;

	@SerializedName("geom")
	private Geom geom;

	@SerializedName("rural_district")
	private String ruralDistrict;

	@SerializedName("province")
	private String province;

	@SerializedName("neighbourhood")
	private String neighbourhood;

	@SerializedName("district")
	private String district;

	@SerializedName("name")
	private String name;

	@SerializedName("village")
	private String village;

	@SerializedName("region")
	private String region;

	@SerializedName("postal_code")
	private String postalCode;

	@SerializedName("primary")
	private String primary;

	public void setPostalAddress(String postalAddress){
		this.postalAddress = postalAddress;
	}

	public String getPostalAddress(){
		return postalAddress;
	}

	public void setCountry(String country){
		this.country = country;
	}

	public String getCountry(){
		return country;
	}

	public void setPlaque(String plaque){
		this.plaque = plaque;
	}

	public String getPlaque(){
		return plaque;
	}

	public void setAddress(String address){
		this.address = address;
	}

	public String getAddress(){
		return address;
	}

	public void setLast(String last){
		this.last = last;
	}

	public String getLast(){
		return last;
	}

	public void setAddressCompact(String addressCompact){
		this.addressCompact = addressCompact;
	}

	public String getAddressCompact(){
		return addressCompact;
	}

	public void setCity(String city){
		this.city = city;
	}

	public String getCity(){
		return city;
	}

	public void setCounty(String county){
		this.county = county;
	}

	public String getCounty(){
		return county;
	}

	public void setPoi(String poi){
		this.poi = poi;
	}

	public String getPoi(){
		return poi;
	}

	public void setGeom(Geom geom){
		this.geom = geom;
	}

	public Geom getGeom(){
		return geom;
	}

	public void setRuralDistrict(String ruralDistrict){
		this.ruralDistrict = ruralDistrict;
	}

	public String getRuralDistrict(){
		return ruralDistrict;
	}

	public void setProvince(String province){
		this.province = province;
	}

	public String getProvince(){
		return province;
	}

	public void setNeighbourhood(String neighbourhood){
		this.neighbourhood = neighbourhood;
	}

	public String getNeighbourhood(){
		return neighbourhood;
	}

	public void setDistrict(String district){
		this.district = district;
	}

	public String getDistrict(){
		return district;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setVillage(String village){
		this.village = village;
	}

	public String getVillage(){
		return village;
	}

	public void setRegion(String region){
		this.region = region;
	}

	public String getRegion(){
		return region;
	}

	public void setPostalCode(String postalCode){
		this.postalCode = postalCode;
	}

	public String getPostalCode(){
		return postalCode;
	}

	public void setPrimary(String primary){
		this.primary = primary;
	}

	public String getPrimary(){
		return primary;
	}

	@Override
 	public String toString(){
		return 
			"GetAddressResponse{" + 
			"postal_address = '" + postalAddress + '\'' + 
			",country = '" + country + '\'' + 
			",plaque = '" + plaque + '\'' + 
			",address = '" + address + '\'' + 
			",last = '" + last + '\'' + 
			",address_compact = '" + addressCompact + '\'' + 
			",city = '" + city + '\'' + 
			",county = '" + county + '\'' + 
			",poi = '" + poi + '\'' + 
			",geom = '" + geom + '\'' + 
			",rural_district = '" + ruralDistrict + '\'' + 
			",province = '" + province + '\'' + 
			",neighbourhood = '" + neighbourhood + '\'' + 
			",district = '" + district + '\'' + 
			",name = '" + name + '\'' + 
			",village = '" + village + '\'' + 
			",region = '" + region + '\'' + 
			",postal_code = '" + postalCode + '\'' + 
			",primary = '" + primary + '\'' + 
			"}";
		}
}