package com.kavinoo.kavinoo.onlinedata.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.kavinoo.kavinoo.onlinedata.model.slider.SlidesResponse;
import com.kavinoo.kavinoo.onlinedata.repository.RepositorySlider;

import java.util.ArrayList;

public class SliderViewModel extends AndroidViewModel {

    private RepositorySlider repositorySlider;

    public SliderViewModel(@NonNull Application application) {
        super(application);
        repositorySlider=new RepositorySlider(application);
    }

    public LiveData<SlidesResponse> getSliderListLData(String url){
        return repositorySlider.getSliderListLData(url);
    }



}