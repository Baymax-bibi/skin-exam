package com.skinexam.myapplication.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by webwerks on 23/5/15.
 */
public class ProfileDataModel extends BaseResponseModel {

//    @SerializedName("student")
//    private Student student;
//
//    public Student getStudent() {
//        return student;
//    }
//
//    public class Student{

        @SerializedName("token")
        private String token;


        @SerializedName("firstname")
        private String fName;

        @SerializedName("lastname")
        private String lName;

        @SerializedName("address")
        private String address;

        @SerializedName("city")
        private String city;

        @SerializedName("state")
        private String state;

        @SerializedName("zipcode")
        private String zipCode;

        @SerializedName("mob_no")
        private String mobileNo;

        @SerializedName("tel_no")
        private String homeTelNo;

        @SerializedName("email")
        private String email;

        @SerializedName("subs")
        private String subs;

        @SerializedName("country_name")
        private String countryName;

        @SerializedName("country_id")
        private String country_id;


    public String getfName() {
            return fName;
        }

        public String getlName() {
            return lName;
        }

        public String getAddress() {
            return address;
        }

        public String getCity() {
            return city;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getZipCode() {
            return zipCode;
        }

        public String getMobileNo() {
            return mobileNo;
        }

        public String getHomeTelNo() {
            return homeTelNo;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public void setSubs(String subs) {
            this.subs = subs;
        }

        public String getCountryName() {
            return countryName;
        }

        public void setCountryName(String countryName) {
            this.countryName = countryName;
        }

        public String getToken() {
            return token;
        }

    public String getCountry_id() {
        return country_id;
    }

    public void setCountry_id(String country_id) {
        this.country_id = country_id;
    }
//    }
}
