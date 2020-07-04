package com.skinexam.myapplication.ui.logout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.skinexam.myapplication.R;
import com.skinexam.myapplication.splah.SignInActivity;
import com.skinexam.myapplication.utils.Constants;

import static android.content.Context.MODE_PRIVATE;

public class LogOutFragment extends Fragment {

    private LogOutViewModel sendViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        sendViewModel =
                ViewModelProviders.of(this).get(LogOutViewModel.class);
        View root = inflater.inflate(R.layout.fragment_logout, container, false);
        final TextView textView = root.findViewById(R.id.text_send);
        sendViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        insertBoolean("SaveLogin", false);
        insertString(Constants.TOKEN, "-1");
        SharedPreferences mSharedPreferences = getContext().getSharedPreferences(Constants.LOGIN_PREF, MODE_PRIVATE);
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.clear();

       getActivity().startActivity(new Intent(getActivity(), SignInActivity.class));
        getActivity().finish();

        return root;
    }
    public synchronized void insertBoolean(String key, boolean value) {
        SharedPreferences mSharedPreferences = getContext().getSharedPreferences(Constants.LOGIN_PREF, MODE_PRIVATE);
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.putBoolean(key, value);
        mEditor.apply();
    }
    public synchronized void insertString(String key, String value) {
        SharedPreferences mSharedPreferences = getContext().getSharedPreferences(Constants.LOGIN_PREF, MODE_PRIVATE);
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.putString(key, value);
        mEditor.apply();
    }
}