package com.skinexam.myapplication.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class PatientStateModel
{

    @SerializedName("patient_health")

    private List<PatientState> patientState = new ArrayList<PatientState>();

    public List<PatientState> getPatientState()
    {
        return patientState;
    }

}