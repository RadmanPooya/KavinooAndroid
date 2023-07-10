package com.kavinoo.kavinoo.localdata.model.intro;

import com.google.gson.annotations.SerializedName;

public class IntroItem {

	@SerializedName("intro_image")
	private int introImage;

	@SerializedName("primary_text")
	private String primaryText;

	@SerializedName("secondary_text")
	private String secondaryText;


	public IntroItem(int introImage, String primaryText, String secondaryText) {
		this.introImage = introImage;
		this.primaryText = primaryText;
		this.secondaryText = secondaryText;
	}

	public void setIntroImage(int introImage) {
		this.introImage = introImage;
	}

	public void setPrimaryText(String primaryText) {
		this.primaryText = primaryText;
	}

	public void setSecondaryText(String secondaryText) {
		this.secondaryText = secondaryText;
	}

	public int getIntroImage() {
		return introImage;
	}

	public String getPrimaryText() {
		return primaryText;
	}

	public String getSecondaryText() {
		return secondaryText;
	}
}