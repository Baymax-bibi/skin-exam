package com.skinexam.myapplication.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by webwerks on 9/5/15.
 */
public class Ticket {
    private List<String> image = new ArrayList<String>();
    private List<Object> description = new ArrayList<Object>();

    @SerializedName("title")
    private String title;

    @SerializedName("summary")
    private String summary;


//    @SerializedName("")
//    private String title;

    @SerializedName("treatment")
    private String treatment;

    @SerializedName("age")
    private String age;

    @SerializedName("doctor_reply")
    private String doctorReply;

    @SerializedName("doctor_reply_status")
    private String doctorReplyStatus;

    @SerializedName("health_status")
    private String healthStatus;

    @SerializedName("doc_firstname")
    private Object docFirstname;

    @SerializedName("doc_lastname")
    private Object docLastname;


    public List<String> getImage() {
        return image;
    }


    public void setImage(List<String> image) {
        this.image = image;
    }


    public List<Object> getDescription() {
        return description;
    }


    public void setDescription(List<Object> description) {
        this.description = description;
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


    public String getAge() {
        return age;
    }


    public void setAge(String age) {
        this.age = age;
    }


    public String getDoctorReply() {
        return doctorReply;
    }


    public void setDoctorReply(String doctorReply) {
        this.doctorReply = doctorReply;
    }


    public String getDoctorReplyStatus() {
        return doctorReplyStatus;
    }


    public void setDoctorReplyStatus(String doctorReplyStatus) {
        this.doctorReplyStatus = doctorReplyStatus;
    }


    public String getHealthStatus() {
        return healthStatus;
    }


    public void setHealthStatus(String healthStatus) {
        this.healthStatus = healthStatus;
    }


    public Object getDocFirstname() {
        return docFirstname;
    }


    public void setDocFirstname(Object docFirstname) {
        this.docFirstname = docFirstname;
    }


    public Object getDocLastname() {
        return docLastname;
    }

    public void setDocLastname(Object docLastname) {
        this.docLastname = docLastname;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }
}