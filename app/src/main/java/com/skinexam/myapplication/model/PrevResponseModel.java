package com.skinexam.myapplication.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class PrevResponseModel 
{

@SerializedName("patient_prev")
private List<PatientPrev> patientPrev = new ArrayList<PatientPrev>();

public List<PatientPrev> getPatientPrev()
{
return patientPrev;
}

}