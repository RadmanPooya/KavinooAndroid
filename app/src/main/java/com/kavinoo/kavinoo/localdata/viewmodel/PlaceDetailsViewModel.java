package com.kavinoo.kavinoo.localdata.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.kavinoo.kavinoo.localdata.model.placedetail.PlaceDetails;
import com.kavinoo.kavinoo.localdata.repository.PlaceDetailsRepository;
import com.kavinoo.kavinoo.localdata.task.PlaceDetailsInsertAsyncTask;

import java.util.List;

public class PlaceDetailsViewModel extends AndroidViewModel {

    private PlaceDetailsRepository placeDetailsRepository;

    public PlaceDetailsViewModel(@NonNull Application application) {
        super(application);
        placeDetailsRepository = new PlaceDetailsRepository(application);

    }
    public void insert(PlaceDetails placeDetails){
        //categoryRepository.insert(categoryModel);
        new PlaceDetailsInsertAsyncTask(placeDetailsRepository).execute(placeDetails);
    }

    public LiveData<List<PlaceDetails>> allFavorite(){
        return placeDetailsRepository.allFavorite();
    }

}
