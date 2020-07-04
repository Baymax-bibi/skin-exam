package com.skinexam.myapplication.ui.changepwd;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ChangePwdViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ChangePwdViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is change password fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}