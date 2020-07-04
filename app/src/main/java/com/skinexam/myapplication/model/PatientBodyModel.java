package com.skinexam.myapplication.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class PatientBodyModel
{

    @SerializedName("patient_body")

    private List<PatientState> patientState = new ArrayList<PatientState>();

    public List<PatientState> getPatientState()
    {
        return patientState;
    }

}