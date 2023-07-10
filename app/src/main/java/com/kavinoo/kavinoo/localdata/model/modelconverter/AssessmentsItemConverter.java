package com.kavinoo.kavinoo.localdata.model.modelconverter;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kavinoo.kavinoo.localdata.model.placedetail.AssessmentsItem;

import java.util.List;

public class AssessmentsItemConverter {
    @TypeConverter
    public String fromAssessmentsItemToJson(List<AssessmentsItem> assessmentsItem) {
        Gson gson = new Gson();
        String json = gson.toJson(assessmentsItem);
        return json;
    }

    @TypeConverter
    public List<AssessmentsItem> toAssessmentsItemFromJson(String json) {
        Gson gson = new Gson();
        List<AssessmentsItem> assessmentsItem = gson.fromJson(json, new TypeToken<List<AssessmentsItem>>(){}.getType());
        return assessmentsItem;
    }
}
