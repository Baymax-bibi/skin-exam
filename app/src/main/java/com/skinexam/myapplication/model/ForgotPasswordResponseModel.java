package com.skinexam.myapplication.model;


import com.google.gson.annotations.SerializedName;

public class ForgotPasswordResponseModel
{

    @SerializedName("status")
    private Boolean status;

    @SerializedName("msg")
    private String msg;

    @SerializedName("password")
    private String password;


    public ForgotPasswordResponseModel() {
    }

    public Boolean getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public String getPassword() {
        return password;
    }
}
