package com.kavinoo.kavinoo.onlinedata.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.kavinoo.kavinoo.onlinedata.model.place.placelist.PlacesResponse;
import com.kavinoo.kavinoo.onlinedata.repository.RepositoryPlace;

public class PlaceViewModel extends AndroidViewModel {

    private RepositoryPlace repositoryPlace;

    public PlaceViewModel(@NonNull Application application) {
        super(application);
        repositoryPlace=new RepositoryPlace(application);
    }
    public LiveData<PlacesResponse> getPlacesListLData(String url){
        return repositoryPlace.getPlacesListLData(url);
    }

}
