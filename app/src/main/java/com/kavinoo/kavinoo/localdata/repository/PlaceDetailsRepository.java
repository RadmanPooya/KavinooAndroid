package com.kavinoo.kavinoo.localdata.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.kavinoo.kavinoo.localdata.db.AppDao;
import com.kavinoo.kavinoo.localdata.db.AppDatabase;
import com.kavinoo.kavinoo.localdata.model.placedetail.PlaceDetails;

import java.util.List;

public class PlaceDetailsRepository {
    private AppDao appDao;


    public PlaceDetailsRepository(Context context) {
        AppDatabase appDatabase = AppDatabase.getInstance(context);
        appDao = appDatabase.appDao();
    }

    public void insert(PlaceDetails placeDetails) {
        appDao.insert(placeDetails);
    }

    public LiveData<List<PlaceDetails>> allFavorite() {
        return appDao.allFavorite();
    }

}
