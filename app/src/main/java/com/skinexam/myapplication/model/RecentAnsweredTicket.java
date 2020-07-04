package com.skinexam.myapplication.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class RecentAnsweredTicket extends BaseTicket
{


    private String id;

    private String title;

    private String summary;
    @SerializedName("doctor_reply_status")
    private String doctorReplyStatus;
    @SerializedName("ticket_description")

    private String ticketDescription;
    @SerializedName("created_date")

    private String createdDate;

//    private List<String> image = new ArrayList<String>();


    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getSummary() {
        return summary;
    }
    public void setSummary(String summary) {
        this.summary = summary;
    }
    public String getDoctorReplyStatus() {
        return doctorReplyStatus;
    }
    public void setDoctorReplyStatus(String doctorReplyStatus) {
        this.doctorReplyStatus = doctorReplyStatus;
    }

    public String getTicketDescription() {
        return ticketDescription;
    }
    public void setTicketDescription(String ticketDescription) {
        this.ticketDescription = ticketDescription;
    }
    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }
//    public List<String> getImage() {
//        return image;
//    }
//    public void setImage(List<String> image) {
//        this.image = image;
//    }
    public String getCreatedDate() {
        return createdDate;
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

//    @Override
//    public List<String> getTicketImage() {
//        return image;
//    }

    @Override
    public String getTicketDesc() {
        return ticketDescription;
    }


}
