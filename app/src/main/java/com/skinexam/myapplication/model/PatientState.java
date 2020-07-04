package com.skinexam.myapplication.model;


import com.google.gson.annotations.SerializedName;

public class PatientState {
    @SerializedName("id")
    private Integer id;
    @SerializedName("status")
    private String status;

    private boolean isSelected;

    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }


    public void setStatus(String status) {
        this.status = status;
    }

}