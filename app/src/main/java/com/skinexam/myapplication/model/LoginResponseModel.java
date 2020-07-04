package com.skinexam.myapplication.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class LoginResponseModel {
    @SerializedName("status")
    private Boolean status;

    @SerializedName("user_id")
    private String userId;


    @SerializedName("token")
    private String token;

    @SerializedName("session_id")
    private String sessionId;

    @SerializedName("pending_tickets")
    private List<PendingTicket> pendingTickets = new ArrayList<PendingTicket>();

    @SerializedName("recent_tickets")
    private List<RecentTicket> recentTickets = new ArrayList<RecentTicket>();

    private List<RecentAnsweredTicket> allTickets_re = new ArrayList<RecentAnsweredTicket>();
    private List<PendingTicket> allTickets_pe = new ArrayList<PendingTicket>();
//
    @SerializedName("all_tickets_count")
    private Integer allTicketsCount;

    @SerializedName("all_count")
    private Integer allCount;

    @SerializedName("pending_tickets_count")
    private Integer pendingTicketsCount;

    @SerializedName("pending_count")
    private Integer pendingCount;

    private String msg,subs;

    @SerializedName("recent_tickets_count")
    private Integer recentTicketsCount;

    @SerializedName("recent_count")
    private Integer recentCount;

    @SerializedName("treatment")
    private Integer treatment;

    public LoginResponseModel() {
    }

    public Boolean getStatus() {
        return status;
    }

    public String getUserId() {
        return userId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public String getMsg() {
        return msg;
    }

    public List<PendingTicket> getPendingTickets() {
        return pendingTickets;
    }

//    public List<RecentAnsweredTicket> getRecentAnsweredTickets() {
//        return recentAnsweredTickets;
//    }

    public Integer getAllTicketsCount() {
        return allTicketsCount;
    }

    public Integer getPendingTicketsCount() {
        return pendingTicketsCount;
    }

    public Integer getRecentTicketsCount() {
        return recentTicketsCount;
    }

    public List<RecentTicket> getAllTickets_re() {
        return this.recentTickets;
    }

    public List<PendingTicket> getAllTickets_pe() {
        return this.pendingTickets;
    }

    public Integer getAllCount() {
        return allCount;
    }

    public Integer getPendingCount() {
        return pendingCount;
    }

    public Integer getRecentCount() {
        return recentCount;
    }

    public String getToken() {
        return token;
    }

    public List<RecentTicket> getRecentTickets() {
        return recentTickets;
    }

    public Integer getTreatment() {
        return treatment;
    }

    public void setTreatment(Integer treatment) {
        this.treatment = treatment;
    }
}






