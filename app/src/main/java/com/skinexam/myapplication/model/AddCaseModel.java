package com.skinexam.myapplication.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by satyawan on 28/4/15 for Add case Data.
 */
public class AddCaseModel implements Serializable {
    private String userId;
    private String sessionId;
    private String title;
    private String summery;
    private String image1;
    private String image2;
    private String image3;
    private String payby;
    private String paypal;
    private String description1;
    private String description2;
    private String description3;
    private String img_extension1;
    private String img_extension2;
    private String img_extension3;
    private String age;
    private String state;
    private PatientHealth patientHealth;
    private List<PatientState> patientStates;
    List<String> imgArrayList;
    private boolean fromPreview;

    public boolean isFromPreview() {
        return fromPreview;
    }

    public void setFromPreview(boolean fromPreview) {
        this.fromPreview = fromPreview;
    }

    public PatientHealth getPatientHealth() {
        return patientHealth;
    }

    public void setPatientHealth(PatientHealth patientHealth) {
        this.patientHealth = patientHealth;
    }

    public List<PatientState> getPatientStates() {
        return patientStates;
    }

    public void setPatientStates(List<PatientState> patientStates) {
        this.patientStates = patientStates;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    private String ticket_description;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummery() {
        return summery;
    }

    public void setSummery(String summery) {
        this.summery = summery;
    }

    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }

    public String getImage3() {
        return image3;
    }

    public void setImage3(String image3) {
        this.image3 = image3;
    }

    public String getPaypal() {
        return paypal;
    }

    public void setPaypal(String paypal) {
        this.paypal = paypal;
    }

    public String getPayby() {
        return payby;
    }

    public void setPayby(String payby) {
        this.payby = payby;
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

    public String getImg_extension1() {
        return img_extension1;
    }

    public void setImg_extension1(String img_extension1) {
        this.img_extension1 = img_extension1;
    }

    public String getImg_extension2() {
        return img_extension2;
    }

    public void setImg_extension2(String img_extension2) {
        this.img_extension2 = img_extension2;
    }

    public String getImg_extension3() {
        return img_extension3;
    }

    public void setImg_extension3(String img_extension3) {
        this.img_extension3 = img_extension3;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getTicket_description() {
        return ticket_description;
    }

    public void setTicket_description(String ticket_description) {
        this.ticket_description = ticket_description;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<String> getImgArrayList() {
        return imgArrayList;
    }

    public void setImgArrayList(List<String> imgArrayList) {
        this.imgArrayList = imgArrayList;
    }

}
