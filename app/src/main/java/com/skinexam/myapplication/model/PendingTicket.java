package com.skinexam.myapplication.model;


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class PendingTicket extends BaseTicket {


    @SerializedName("id")
    private String id;

    @SerializedName("title")
    private String title;

    @SerializedName("doctor_reply_status")
    private String doctorReplyStatus;

    @SerializedName("ticket_description")
    private String ticketDescription;

    @SerializedName("created_date")
    private String createdDate;


    @SerializedName("image1")
    private String image1;

    @SerializedName("image2")
    private String image2;

    @SerializedName("image3")
    private String image3;

    @SerializedName("image")
//    private List<String> image = new ArrayList<String>();
    private List<Image_Model> image = new ArrayList<Image_Model>();

    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }


    public void setTitle(String title) {
        this.title = title;
    }


//    public void setSummary(String summary) {
//        this.summary = summary;
//    }

    public String getTitle() {
        return title;
    }


    public void setDoctorReplyStatus(String doctorReplyStatus) {
        this.doctorReplyStatus = doctorReplyStatus;
    }


    public void setTicketDescription(String ticketDescription) {
        this.ticketDescription = ticketDescription;
    }


    @Override
    public String getTicketId() {
        return id;
    }

    @Override
    public String getTicketTitle() {
        return title;
    }

    @Override
    public String getImage_1() {
        return image1;
    }

    @Override
    public String getImage_2() {
        return image2;
    }

    @Override
    public String getImage_3() {
        return image3;
    }

    @Override
    public List<String> getTicketImage() {
        return null;
    }

    @Override
    public String getDate() {
        return createdDate;
    }

    public String getCreatedDate() {
        return createdDate;
    }


    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }


    @Override
    public String getTicketDesc() {
        return ticketDescription;
    }

    public List<Image_Model> getImage() {
        return image;
    }

    public void setImage(List<Image_Model> image) {
        this.image = image;
    }

    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }

    public String getImage3() {
        return image3;
    }

    public void setImage3(String image3) {
        this.image3 = image3;
    }
}
