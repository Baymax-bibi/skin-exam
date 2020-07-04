package com.skinexam.myapplication.ui.editprofile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class EditprofileViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public EditprofileViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is edit profile fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}