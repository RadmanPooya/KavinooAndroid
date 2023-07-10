package com.kavinoo.kavinoo.localdata.model.modelconverter;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kavinoo.kavinoo.localdata.model.placedetail.Area;

public class AreaConverter {
    @TypeConverter
    public String fromAreaToJson(Area area) {
        Gson gson = new Gson();
        String json = gson.toJson(area);
        return json;
    }

    @TypeConverter
    public Area toAreaFromJson(String json) {
        Gson gson = new Gson();
        Area area = gson.fromJson(json, new TypeToken<Area>(){}.getType());
        return area;
    }
}
