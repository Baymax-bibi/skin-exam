
package com.skinexam.myapplication.model;

import java.util.ArrayList;
import java.util.List;

public class PaymodeResponseModel
{

    private Boolean status;
    private Integer payPal;
    private List<Dropdown> dropdown = new ArrayList<Dropdown>();
    private List<Insurance> insurance = new ArrayList<Insurance>();
    private List<Corporate> corporate = new ArrayList<Corporate>();

    public Boolean getStatus() {
        return status;
    }


    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Integer getPayPal() {
        return payPal;
    }


    public void setPayPal(Integer payPal) {
        this.payPal = payPal;
    }


    public List<Dropdown> getDropdown() {
        return dropdown;
    }


    public void setDropdown(List<Dropdown> dropdown) {
        this.dropdown = dropdown;
    }


    public List<Insurance> getInsurance() {
        return insurance;
    }


    public void setInsurance(List<Insurance> insurance) {
        this.insurance = insurance;
    }


    public List<Corporate> getCorporate() {
        return corporate;
    }


    public void setCorporate(List<Corporate> corporate) {
        this.corporate = corporate;
    }

}