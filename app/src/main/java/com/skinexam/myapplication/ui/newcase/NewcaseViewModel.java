package com.skinexam.myapplication.ui.newcase;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NewcaseViewModel extends ViewModel {

    private MutableLiveData<String> mText, mText1;

    public NewcaseViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is new case fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}