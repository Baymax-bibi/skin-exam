package com.skinexam.myapplication.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by webwerks1 on 10/4/15.(Register Response)
 */

public class BaseResponseModel {

    @SerializedName("error_msg")
    private String errorMsg;
    @SerializedName("status")
    private Boolean status;
    @SerializedName("msg")
    private String msg;
    @SerializedName("success")
    private String success;

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getSuccess(){
        return success;
    }

    public void setSuccess(String success)

    {
        this.success = success;
    }
}




