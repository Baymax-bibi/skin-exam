package com.skinexam.myapplication.model;


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Case extends BaseTicket {

     
    private String id;
     
    private String title;
     
    private String summary;

    @SerializedName("doctor_reply_status")
    private String doctorReplyStatus;

    @SerializedName("ticket_description")
    private String ticketDescription;

    @SerializedName("created_date")
    private String createdDate;

    @SerializedName("ticket_image")
    private String ticketImage;

    /**
     * @return The id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return The summary
     */
    public String getSummary() {
        return summary;
    }

    /**
     * @param summary The summary
     */
    public void setSummary(String summary) {
        this.summary = summary;
    }

    /**
     * @return The doctorReplyStatus
     */
    public String getDoctorReplyStatus() {
        return doctorReplyStatus;
    }

    /**
     * @param doctorReplyStatus The doctor_reply_status
     */
    public void setDoctorReplyStatus(String doctorReplyStatus) {
        this.doctorReplyStatus = doctorReplyStatus;
    }

    /**
     * @return The ticketDescription
     */
    public String getTicketDescription() {
        return ticketDescription;
    }

    /**
     * @param ticketDescription The ticket_description
     */
    public void setTicketDescription(String ticketDescription) {
        this.ticketDescription = ticketDescription;
    }

    /**
     * @return The createdDate
     */
    public String getCreatedDate() {
        return createdDate;
    }

    /**
     * @param createdDate The created_date
     */
    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
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
        ArrayList<String> imgList = new ArrayList<>();
        imgList.add(ticketImage);
        return imgList;
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
