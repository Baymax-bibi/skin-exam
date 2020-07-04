
package com.skinexam.myapplication.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class StudentTicketModel {


    private Boolean status;

    private String msg;

//    private ProfileDataModel.Student student;
    @SerializedName("subs_msg")

    private String subsMsg;

    private List<Case> cases = new ArrayList<Case>();

    /**
     * @return The status
     */
    public Boolean getStatus() {
        return status;
    }

    /**
     * @param status The status
     */
    public void setStatus(Boolean status) {
        this.status = status;
    }

    /**
     * @return The msg
     */
    public String getMsg() {
        return msg;
    }

    /**
     * @param msg The msg
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * @return The student
     */
//    public ProfileDataModel.Student getStudent() {
//        return student;
//    }

    /**
     * @param student The student
     */
//    public void setStudent(ProfileDataModel.Student student) {
//        this.student = student;
//    }

    /**
     * @return The subsMsg
     */
    public String getSubsMsg() {
        return subsMsg;
    }

    /**
     * @param subsMsg The subs_msg
     */
    public void setSubsMsg(String subsMsg) {
        this.subsMsg = subsMsg;
    }

    /**
     * @return The cases
     */
    public List<Case> getCases() {
        return cases;
    }

    /**
     * @param cases The cases
     */
    public void setCases(List<Case> cases) {
        this.cases = cases;
    }
}
