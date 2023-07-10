package com.kavinoo.kavinoo.onlinedata.model.addassessment;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class AddAssessmentModel{

	@SerializedName("assessments")
	private List<AssessmentsItem> assessments;

	@SerializedName("place_id")
	private String placeId;

	public void setAssessments(List<AssessmentsItem> assessments){
		this.assessments = assessments;
	}

	public List<AssessmentsItem> getAssessments(){
		return assessments;
	}

	public void setPlaceId(String placeId){
		this.placeId = placeId;
	}

	public String getPlaceId(){
		return placeId;
	}

	@Override
 	public String toString(){
		return 
			"AddAssessmentModel{" + 
			"assessments = '" + assessments + '\'' + 
			",place_id = '" + placeId + '\'' + 
			"}";
		}
}