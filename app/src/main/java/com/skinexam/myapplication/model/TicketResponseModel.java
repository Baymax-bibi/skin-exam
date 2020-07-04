package com.skinexam.myapplication.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class TicketResponseModel {

    private Boolean status;

    @SerializedName("ticket")
    private Ticket ticket;

    @SerializedName("patient_state")
    private List<String> patientState = new ArrayList<String>();

    public Boolean getStatus() {
        return status;
    }


    public void setStatus(Boolean status) {
        this.status = status;
    }


    public Ticket getTicket() {
        return ticket;
    }


    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }


    public List<String> getPatientState() {
        return patientState;
    }


    public void setPatientState(List<String> patientState) {
        this.patientState = patientState;
    }

}