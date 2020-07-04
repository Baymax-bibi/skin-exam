package com.skinexam.myapplication.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Image_Model  extends BaseTicket {

    @SerializedName("image1")
    private String image1;

    @SerializedName("image2")
    private String image2;

    @SerializedName("image3")
    private String image3;

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }

    public void setImage3(String image3) {
        this.image3 = image3;
    }

    @Override
    public String getTicketId() {
        return null;
    }

    @Override
    public String getTicketTitle() {
        return null;
    }

    @Override
    public String getImage_1() {
        return null;
    }

    @Override
    public String getImage_2() {
        return null;
    }

    @Override
    public String getImage_3() {
        return null;
    }

    @Override
    public List<String> getTicketImage() {
        return null;
    }

    @Override
    public String getDate() {
        return null;
    }

    @Override
    public String getTicketDesc() {
        return null;
    }
}
