package com.skinexam.myapplication.model;

import com.google.gson.annotations.SerializedName;


public class RegisterResponseModel {
//    @SerializedName("error_msg")
//    private String errorMsg;
    @SerializedName("status")
    private Boolean status;
    @SerializedName("msg")
    private String msg;
    @SerializedName("success")
    private String success;


//    public String getErrorMsg() {
//        return errorMsg;
//    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public String getSuccess()

    {
        return success;
    }


}
