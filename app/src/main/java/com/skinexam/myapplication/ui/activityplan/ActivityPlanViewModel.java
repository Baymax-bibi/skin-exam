package com.skinexam.myapplication.ui.activityplan;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ActivityPlanViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ActivityPlanViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is my Activity plan fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}