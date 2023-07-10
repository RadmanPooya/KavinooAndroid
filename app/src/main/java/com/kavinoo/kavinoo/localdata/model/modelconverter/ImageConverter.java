package com.kavinoo.kavinoo.localdata.model.modelconverter;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kavinoo.kavinoo.localdata.model.placedetail.Image;

public class ImageConverter {
    @TypeConverter
    public String fromImageToJson(Image image) {
        Gson gson = new Gson();
        String json = gson.toJson(image);
        return json;
    }

    @TypeConverter
    public Image toImageFromJson(String json) {
        Gson gson = new Gson();
        Image image = gson.fromJson(json, new TypeToken<Image>(){}.getType());
        return image;
    }
}
