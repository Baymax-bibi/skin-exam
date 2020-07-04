package com.skinexam.myapplication.model;


import com.google.gson.annotations.SerializedName;

public class CreateCaseResultModel {
    @SerializedName("token")
    private String token;
    @SerializedName("status")
    private String status;
    @SerializedName("image_msg")
    private String image_msg;

    @SerializedName("image_1")
    private String image_1;




    public String getToken() {
        return token;
    }

    public String getStatus() {
        return status;
    }

    public String getImage_msg() {
        return image_msg;
    }

    public String getImage_1() {
        return image_1;
    }

    public void setImage_1(String image_1) {
        this.image_1 = image_1;
    }
}