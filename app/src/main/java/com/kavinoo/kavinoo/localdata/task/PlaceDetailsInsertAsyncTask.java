package com.kavinoo.kavinoo.localdata.task;

import android.os.AsyncTask;

import com.kavinoo.kavinoo.localdata.model.placedetail.PlaceDetails;
import com.kavinoo.kavinoo.localdata.repository.PlaceDetailsRepository;

public class PlaceDetailsInsertAsyncTask extends AsyncTask<PlaceDetails,Void,Void> {

    private PlaceDetailsRepository placeDetailsRepository;

    public PlaceDetailsInsertAsyncTask(PlaceDetailsRepository placeDetailsRepository) {
        this.placeDetailsRepository = placeDetailsRepository;
    }

    @Override
    protected Void doInBackground(PlaceDetails... placeDetails) {
        placeDetailsRepository.insert(placeDetails[0]);
        return null;
    }
}
