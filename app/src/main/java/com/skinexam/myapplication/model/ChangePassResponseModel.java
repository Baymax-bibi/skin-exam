package com.skinexam.myapplication.model;


import com.google.gson.annotations.SerializedName;

public class ChangePassResponseModel
{
 @SerializedName("status")
 private Boolean status;
 @SerializedName("msg")
private String msg;

    public Boolean getStatus()
{
return status;
}

public void setStatus(Boolean status)
{
this.status = status;
}


public String getMsg()
{
return msg;
}


public void setMsg(String msg)
{
this.msg = msg;
}

}