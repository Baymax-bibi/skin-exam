package com.skinexam.myapplication.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class CountryResponseModel
{


    @SerializedName("country")
private List<Country> country = new ArrayList<Country>();

/**
* 
* @return
* The country
*/
public List<Country> getCountry() {
return country;
}

}