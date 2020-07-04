package com.skinexam.myapplication.model;

public class MyCheckBoxModel {

    private boolean isSelected;
    private String state;

    public String getState(){
        return state;
    }
    public void setState(String state){
        this.state = state;
    }

    public boolean getSelected(){
        return isSelected;
    }
    public void setSelected(boolean selected){
        isSelected = selected;
    }

}
