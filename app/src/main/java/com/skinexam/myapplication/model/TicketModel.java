package com.skinexam.myapplication.model;

import com.google.gson.annotations.SerializedName;

public class TicketModel {
    @SerializedName("image1")
    private String image1;
    @SerializedName("image2")
    private String image2;
    @SerializedName("image3")
    private String image3;
    @SerializedName("description1")
    private String description1;
    @SerializedName("description2")
    private String description2;
    @SerializedName("description3")
    private String description3;
    @SerializedName("health_status")
    private String health_status;
    @SerializedName("user_body_part")
    private String user_body_part;
    @SerializedName("treatment")
    private String treatment;
    @SerializedName("priortreatment")
    private String priortreatment;
    @SerializedName("title")
    private String title;
    @SerializedName("age")
    private String age;
    @SerializedName("summary")
    private String summary;
    @SerializedName("itchy")
    private String itchy;
    @SerializedName("changing_color")
    private String changing_color;
    @SerializedName("feels_like_bump")
    private String feels_like_bump;
    @SerializedName("status")
    private boolean status;

//    @SerializedName("patient_state")
//    private List<String> patientState = new ArrayList<String>();


    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public String getImage3() {
        return image3;
    }

    public void setImage3(String image3) {
        this.image3 = image3;
    }

    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }

    public String getDescription1() {
        return description1;
    }

    public void setDescription1(String description1) {
        this.description1 = description1;
    }

    public String getDescription2() {
        return description2;
    }

    public void setDescription2(String description2) {
        this.description2 = description2;
    }

    public String getDescription3() {
        return description3;
    }

    public void setDescription3(String description3) {
        this.description3 = description3;
    }

    public String getUser_body_part() {
        return user_body_part;
    }

    public void setUser_body_part(String user_body_part) {
        this.user_body_part = user_body_part;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    public String getPriortreatment() {
        return priortreatment;
    }

    public void setPriortreatment(String priortreatment) {
        this.priortreatment = priortreatment;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getItchy() {
        return itchy;
    }

    public void setItchy(String itchy) {
        this.itchy = itchy;
    }

    public String getChanging_color() {
        return changing_color;
    }

    public void setChanging_color(String changing_color) {
        this.changing_color = changing_color;
    }

    public String getFeels_like_bump() {
        return feels_like_bump;
    }

    public void setFeels_like_bump(String feels_like_bump) {
        this.feels_like_bump = feels_like_bump;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }


    public String getHealth_status() {
        return health_status;
    }

    public void setHealth_status(String health_status) {
        this.health_status = health_status;
    }


}
