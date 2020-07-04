package com.skinexam.myapplication.utils;

import android.text.TextUtils;
import android.widget.EditText;

import com.skinexam.myapplication.model.RegistrationModel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {

    // Regular Expression
    // you can change the expression based on your need
    private static final String PHONE_REGEX = "\\d{3}-\\d{7}";

    // Error Messages
    private static final String REQUIRED_MSG = "Please Enter Field";
    private static final String PHONE_MSG = "###-#######";

    // call this method when you need to check phone number validation
    public static boolean isPhoneNumber(EditText editText, boolean required) {
        return isValid(editText, PHONE_REGEX, PHONE_MSG, required);
    }

    // return true if the input field is valid, based on the parameter passed
    public static boolean isValid(EditText editText, String regex, String errMsg, boolean required) {
        String text = editText.getText().toString().trim();

        // clearing the error, if it was previously set by some other values
        editText.setError(null);

        // text required and editText is blank, so return false
        if (required && !hasText(editText,errMsg)) return false;

        // pattern doesn't match so returning false
        if (required && !Pattern.matches(regex, text)) {
            editText.setError(errMsg);
            return false;
        };
        return true;
    }

    // check the input field has any text or not
    // return true if it contains text otherwise false
    public static boolean hasText(EditText editText,String msg) {
        if (TextUtils.isEmpty(editText.getText().toString())) {
            editText.setError(msg);
            return false;
        } else {
            return true;
        }
    }

    public static boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    
    
    public static boolean isRegistrationComplete(RegistrationModel model){

     if(isNullOrBlank(model.getfName())){

         return false;
     }
        if(isNullOrBlank(model.getlName())){
            return false;
        }
        if(isNullOrBlank(model.getAddress())){
            return false;
        }
        if(isNullOrBlank(model.getCity())){
            return false;
        }
        if(isNullOrBlank(model.getState())){
            return false;
        }
        if(isNullOrBlank(model.getZipCode())){
            return false;
        }
        if(isNullOrBlank(model.getCountryId())){
            return false;
        }



        
        return  true;
        
    }



    public static boolean isNullOrBlank(String string){

        if(string!=null && string.length()>0){
            return true;
        }

    return false;

    }


}