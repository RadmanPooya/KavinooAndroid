package com.kavinoo.kavinoo.localdata.model.modelconverter;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kavinoo.kavinoo.localdata.model.placedetail.CommentsItem;

import java.util.List;

public class CommentsItemConverter {
    @TypeConverter
    public String fromCommentsItemToJson(List<CommentsItem> commentsItem) {
        Gson gson = new Gson();
        String json = gson.toJson(commentsItem);
        return json;
    }

    @TypeConverter
    public List<CommentsItem> toCommentsItemFromJson(String json) {
        Gson gson = new Gson();
        List<CommentsItem> commentsItem = gson.fromJson(json, new TypeToken<List<CommentsItem>>(){}.getType());
        return commentsItem;
    }
}
