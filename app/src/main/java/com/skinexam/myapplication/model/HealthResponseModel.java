package com.skinexam.myapplication.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class HealthResponseModel 
{

@SerializedName("patient_health")
private List<PatientHealth> patientHealth = new ArrayList<PatientHealth>();

public List<PatientHealth> getPatientHealth()
{
return patientHealth;
}

public void setPatientHealth(List<PatientHealth> patientHealth)
{
this.patientHealth = patientHealth;
}

}