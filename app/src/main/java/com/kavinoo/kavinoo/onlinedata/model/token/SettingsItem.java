package com.kavinoo.kavinoo.onlinedata.model.token;

import com.google.gson.annotations.SerializedName;

public class SettingsItem{

	@SerializedName("setting_value")
	private String settingValue;

	@SerializedName("id")
	private int id;

	@SerializedName("setting_key")
	private String settingKey;

	public void setSettingValue(String settingValue){
		this.settingValue = settingValue;
	}

	public String getSettingValue(){
		return settingValue;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setSettingKey(String settingKey){
		this.settingKey = settingKey;
	}

	public String getSettingKey(){
		return settingKey;
	}

	@Override
 	public String toString(){
		return 
			"SettingsItem{" + 
			"setting_value = '" + settingValue + '\'' + 
			",id = '" + id + '\'' + 
			",setting_key = '" + settingKey + '\'' + 
			"}";
		}
}