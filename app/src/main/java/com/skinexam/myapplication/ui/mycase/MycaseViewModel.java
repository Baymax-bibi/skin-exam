package com.skinexam.myapplication.ui.mycase;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MycaseViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MycaseViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is my case fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}