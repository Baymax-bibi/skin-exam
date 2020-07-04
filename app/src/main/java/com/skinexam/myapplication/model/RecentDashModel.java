package com.skinexam.myapplication.model;

import android.content.Context;

public class RecentDashModel {
    private Context context;
    private int image;
    private String content;

    public RecentDashModel(int _img, String _content){
        this.image = _img;
        this.content = _content;
    }

    public int getImage(){
        return image;
    }
    public void setImage(int _img){
        this.image = _img;
    }

    public String getContent(){
        return content;
    }
    public void setContent(String _content){
        this.content = _content;
    }
}
