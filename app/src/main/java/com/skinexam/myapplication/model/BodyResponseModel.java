package com.skinexam.myapplication.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class BodyResponseModel 
{

@SerializedName("body_part")
private List<PatientBody> patientBody = new ArrayList<PatientBody>();

public List<PatientBody> getPatientHealth()
{
return patientBody;
}

}