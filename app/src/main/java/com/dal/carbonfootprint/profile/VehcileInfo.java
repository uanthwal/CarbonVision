package com.dal.carbonfootprint.profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * @author Carbon Vision
 */
public class VehcileInfo extends ViewModel {

    private MutableLiveData<String> mText;

    /**
     * Constructor for vehicleinfo view model
     */
    public VehcileInfo() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}