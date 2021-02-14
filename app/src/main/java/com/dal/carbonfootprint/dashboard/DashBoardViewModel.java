package com.dal.carbonfootprint.dashboard;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * @author Carbon Vision
 */
public class DashBoardViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    /**
     * Constructor for dashboard view model
     */
    public DashBoardViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}