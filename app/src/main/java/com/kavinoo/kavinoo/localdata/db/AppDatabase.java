package com.kavinoo.kavinoo.localdata.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.kavinoo.kavinoo.localdata.model.category.CategoriesItem;
import com.kavinoo.kavinoo.localdata.model.modelconverter.AreaConverter;
import com.kavinoo.kavinoo.localdata.model.modelconverter.AssessmentsItemConverter;
import com.kavinoo.kavinoo.localdata.model.modelconverter.CategoryConverter;
import com.kavinoo.kavinoo.localdata.model.modelconverter.CommentsItemConverter;
import com.kavinoo.kavinoo.localdata.model.modelconverter.DigitalMenusItemConverter;
import com.kavinoo.kavinoo.localdata.model.modelconverter.FacilitiesItemConverter;
import com.kavinoo.kavinoo.localdata.model.modelconverter.ImageConverter;
import com.kavinoo.kavinoo.localdata.model.placedetail.PlaceDetails;

@Database(entities = {CategoriesItem.class, PlaceDetails.class}, version = 7)
@TypeConverters({AreaConverter.class, CategoryConverter.class, CommentsItemConverter.class, DigitalMenusItemConverter.class, FacilitiesItemConverter.class, ImageConverter.class, AssessmentsItemConverter.class})
public abstract class AppDatabase extends RoomDatabase {

    public abstract AppDao appDao();

    private static AppDatabase appDatabase;


    public static AppDatabase getInstance(Context context) {
        if (appDatabase == null) {
            appDatabase = buildDatabase(context);
        }
        return appDatabase;
    }

    private static AppDatabase buildDatabase(Context context) {

        return Room.databaseBuilder(context, AppDatabase.class, "KavinoDb")
                .fallbackToDestructiveMigration()
                .build();
    }

}
