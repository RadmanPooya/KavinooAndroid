package com.kavinoo.kavinoo.localdata.model.modelconverter;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kavinoo.kavinoo.localdata.model.placedetail.DigitalMenusItem;

import java.util.List;

public class DigitalMenusItemConverter {
    @TypeConverter
    public String fromDigitalMenusItemToJson(List<DigitalMenusItem> digitalMenusItem) {
        Gson gson = new Gson();
        String json = gson.toJson(digitalMenusItem);
        return json;
    }

    @TypeConverter
    public List<DigitalMenusItem> toDigitalMenusItemFromJson(String json) {
        Gson gson = new Gson();
        List<DigitalMenusItem> digitalMenusItem = gson.fromJson(json, new TypeToken<List<DigitalMenusItem>>(){}.getType());
        return digitalMenusItem;
    }
}
