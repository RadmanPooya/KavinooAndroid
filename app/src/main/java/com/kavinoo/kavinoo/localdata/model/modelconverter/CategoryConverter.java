package com.kavinoo.kavinoo.localdata.model.modelconverter;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kavinoo.kavinoo.localdata.model.placedetail.Category;

public class CategoryConverter {
    @TypeConverter
    public String fromCategoryToJson(Category category) {
        Gson gson = new Gson();
        String json = gson.toJson(category);
        return json;
    }

    @TypeConverter
    public Category toCategoryFromJson(String json) {
        Gson gson = new Gson();
        Category category = gson.fromJson(json, new TypeToken<Category>(){}.getType());
        return category;
    }
}
