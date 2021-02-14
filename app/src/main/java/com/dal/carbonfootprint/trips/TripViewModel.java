package com.dal.carbonfootprint.trips;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * @Author Carbon vision
 */
public class TripViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    /**
     * Constructor for TripViewModel
     */
    public TripViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}