package com.kavinoo.kavinoo.onlinedata.model.token;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Data{

	@SerializedName("settings")
	private List<SettingsItem> settings;

	@SerializedName("appVersion")
	private Object appVersion;

	@SerializedName("needInformation")
	private boolean needInformation;

	@SerializedName("has_messages")
	private boolean hasMessages;

	@SerializedName("user")
	private User user;

	@SerializedName("token")
	private Token token;

	public void setSettings(List<SettingsItem> settings){
		this.settings = settings;
	}

	public List<SettingsItem> getSettings(){
		return settings;
	}

	public void setAppVersion(Object appVersion){
		this.appVersion = appVersion;
	}

	public Object getAppVersion(){
		return appVersion;
	}

	public void setNeedInformation(boolean needInformation){
		this.needInformation = needInformation;
	}

	public boolean isNeedInformation(){
		return needInformation;
	}

	public void setHasMessages(boolean hasMessages){
		this.hasMessages = hasMessages;
	}

	public boolean isHasMessages(){
		return hasMessages;
	}

	public void setUser(User user){
		this.user = user;
	}

	public User getUser(){
		return user;
	}

	public void setToken(Token token){
		this.token = token;
	}

	public Token getToken(){
		return token;
	}

	@Override
 	public String toString(){
		return 
			"Data{" + 
			"settings = '" + settings + '\'' + 
			",appVersion = '" + appVersion + '\'' + 
			",needInformation = '" + needInformation + '\'' + 
			",has_messages = '" + hasMessages + '\'' + 
			",user = '" + user + '\'' + 
			",token = '" + token + '\'' + 
			"}";
		}
}