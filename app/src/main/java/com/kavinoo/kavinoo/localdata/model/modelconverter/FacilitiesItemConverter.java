package com.kavinoo.kavinoo.localdata.model.modelconverter;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kavinoo.kavinoo.localdata.model.placedetail.FacilitiesItem;

import java.util.List;

public class FacilitiesItemConverter {
    @TypeConverter
    public String fromFacilitiesItemToJson(List<FacilitiesItem> facilitiesItem) {
        Gson gson = new Gson();
        String json = gson.toJson(facilitiesItem);
        return json;
    }

    @TypeConverter
    public List<FacilitiesItem> toFacilitiesItemFromJson(String json) {
        Gson gson = new Gson();
        List<FacilitiesItem> facilitiesItem = gson.fromJson(json, new TypeToken<List<FacilitiesItem>>(){}.getType());
        return facilitiesItem;
    }
}
